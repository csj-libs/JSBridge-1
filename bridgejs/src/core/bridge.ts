import UUID from "uuid/v4";
import { JSCallback } from "./callbacks";
import { Request, Response } from "./models";
import { IProxyListener, Proxy } from "./proxy";
import { Service } from "./service";

export default class Bridge {
    private reqmanager = new RequestManager();
    private servicemanager = new ServiceManager();
    private proxy = new Proxy(((reqm: RequestManager, sm: ServiceManager) =>
        new class ProxyListener implements IProxyListener {
            public onRequest(service: string, req: Request<any>, proxy: Proxy): void {
                const s = sm.find(service);
                if (s) {
                    s.handleNativeRequest(req, proxy);
                }
            }
            public onResponse(uuid: string, res: Response<any>) {
                const cb = reqm.findCallback(uuid);
                if (cb) {
                    cb.delivery(res);
                }
            }
        }()
    )(this.reqmanager, this.servicemanager));

    constructor() {
        // console.log("init");
    }

    /**
     * callNative
     */
    public callNative<T, R>(service: string, param: T): Promise<R> {
        return new Promise<R>(((resolve, reject) => {
            const req = this.reqmanager.newRequest(service, param, (() => {
                return new class AnonymousJSCallback extends JSCallback<R> {
                    public success(result: R): void {
                        resolve(result);
                    }
                    public falied(msg: string): void {
                        reject(msg);
                    }
                    public canceled(msg: string): void {
                        reject(msg);
                    }
                }();
            })());
            this.proxy.request2Native(req);
        }));
    }

    /**
     * registerService
     */
    public registerService(service: Service<any, any>) {
        this.servicemanager.register(service);
    }
}

class RequestManager {
    private outgoingrequests = new Map<string, Request<any>>();
    private outgoingcallbacks = new Map<string, JSCallback<any>>();
    /**
     * newRequest
     */
    public newRequest(service: string, param: any, cb: JSCallback<any>): Request<any> {
        const uuid = UUID();
        const req = new Request(uuid, service, param);
        this.outgoingrequests.set(uuid, req);
        this.outgoingcallbacks.set(uuid, cb);
        return req;
    }
    /**
     * cancel
     */
    public cancel(uuid: string) {
        this.outgoingcallbacks.delete(uuid);
        this.outgoingrequests.delete(uuid);
    }
    /**
     * pickCallback
     */
    public findCallback(uuid: string): JSCallback<any> | undefined {
        const cb = this.outgoingcallbacks.get(uuid);
        this.outgoingcallbacks.delete(uuid);
        this.outgoingrequests.delete(uuid);
        return cb;
    }

}

class ServiceManager {
    private readonly services = new Map<string, Service<any, any>>();
    /**
     * register
     */
    public register<T, R>(service: Service<T, R>) {
        if (!this.services.get(service.name)) {
            this.services.set(service.name, service);
        }
    }

    /**
     * find
     */
    public find(name: string): Service<any, any> | undefined {
        return this.services.get(name);
    }
}

(window as any).bridge = new Bridge();
