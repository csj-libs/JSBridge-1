import { Response } from "./models";
import { Proxy } from "./proxy";
abstract class JSCallback<T> {
    /**
     * delivery
     */
    public delivery(res: Response<T>) {
        switch (res.code) {
            case Response.STATUS_SUCCESS:
                this.success(res.result);
                break;
            case Response.STATUS_FAILED:
                this.falied(res.msg);
                break;
            case Response.STATUS_CANCELED:
                this.canceled(res.msg);
                break;
            default:
                break;
        }
    }
    /**
     * success
     */
    public abstract success(result: T): void;

    /**
     * falied
     */
    public abstract falied(msg: string): void;

    /**
     * canceled
     */
    public abstract canceled(msg: string): void;
}

/**
 * send feed back to native
 */
class NativeCallback<T> {

    constructor(private readonly uuid: string, private readonly proxy: Proxy) { }

    /**
     * success
     */
    public success(result: T) {
        this.send(Response.success("success", result));
    }

    /**
     * falied
     */
    public falied(msg: string = "failed") {
        this.send(Response.failed(msg));
    }

    /**
     * canceled
     */
    public canceled(msg: string = "") {
        this.send(Response.canceled(msg));
    }
    private send(res: Response<(any)>) {
        this.proxy.response2Native(this.uuid, res);
    }

}

export {
    JSCallback,
    NativeCallback,
};
