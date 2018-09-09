import { Request, Response } from "./models";
const REMOTE_PROXY = "REMOTE_PROXY";
const LOCAL_PROXY = "LOCAL_PROXY";
// Proxy this class is mounted on window for Native calling
class Proxy {
    constructor(private readonly listenr: IProxyListener) {
        (window as any)[LOCAL_PROXY] = this;
    }

    /**
     * request2Native
     * @param req request
     */
    public request2Native(req: Request<any>) {
        ((window as any)[REMOTE_PROXY] as IRemoteProxy).requestFromJS(JSON.stringify(req));
    }

    /**
     * response2Native
     * @param uuid unique identifier for the corresponding request from native
     */
    public response2Native(uuid: string, res: Response<any>) {
        ((window as any)[REMOTE_PROXY] as IRemoteProxy).responseFromJS(uuid, JSON.stringify(res));
    }

    /**
     * requestFromNative
     * @param service target service name
     * @param req request entity with uuid、service and params
     */
    public requestFromNative(service: string, req: Request<any>) {
        this.listenr.onRequest(service, req, this);
    }
    /**
     * responseFromNative
     * @param uuid unique identifier for the corresponding request to native
     */
    public responseFromNative(uuid: string, res: Response<any>) {
        this.listenr.onResponse(uuid, res);
    }
}

interface IProxyListener {
    onRequest(service: string, req: Request<any>, proxy: Proxy): void;
    onResponse(uuid: string, res: Response<any>): void;
}

interface IRemoteProxy {
    requestFromJS(reqStr: string): void;
    responseFromJS(uuid: string, resStr: string): void;
}

export {
    Proxy,
    IProxyListener,
};
