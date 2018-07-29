import { NativeCallback } from "./callbacks";
import { Request } from "./models";
import { Proxy } from "./proxy";
abstract class Service<T, R> {
    constructor(readonly name: string) { }
    /**
     * handleNativeRequest
     */
    public handleNativeRequest(request: Request<T>, proxy: Proxy) {
        this.handle(request.param, new NativeCallback(request.uuid, proxy));
    }

    public abstract handle(param: T, callback: NativeCallback<R>): void;
}
class FallbackService extends Service<any, any> {
    public handle(param: any, callback: NativeCallback<any>): void {
        callback.falied("service not found...");
    }
}

export {
    Service,
    FallbackService,
};
