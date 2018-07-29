import { Request, Response } from "./models";
const NATIVE_PROXY = "NATIVE_PROXY";
const JS_PROXY = "JS_PROXY";

class Proxy {
    constructor(private readonly listenr: IProxyListener) {
        (window as any)[JS_PROXY] = this;
    }

    /**
     * request2Native
     * @param req request
     */
    public request2Native(req: Request<any>) {
        ((window as any)[NATIVE_PROXY] as INativeProxy).requestFromJS(JSON.stringify(req));
    }

    /**
     * response2Native
     * @param uuid unique identifier for the coresponding request from native
     */
    public response2Native(uuid: string, res: Response<any>) {
        ((window as any)[NATIVE_PROXY] as INativeProxy).responseFromJS(uuid, JSON.stringify(res));
    }

    /**
     * requestFromNative
     */
    public requestFromNative(service: string, req: Request<any>) {
        this.listenr.onRequest(service, req, this);
    }
    /**
     * responseFromNative
     */
    public responseFromNative(uuid: string, res: Response<any>) {
        this.listenr.onResponse(uuid, res);
    }
}

interface IProxyListener {
    onRequest(service: string, req: Request<any>, proxy: Proxy): void;
    onResponse(uuid: string, res: Response<any>): void;
}

interface INativeProxy {
    requestFromJS(reqStr: string): void;
    responseFromJS(uuid: string, resStr: string): void;
}
export {
    Proxy,
    IProxyListener,
};
