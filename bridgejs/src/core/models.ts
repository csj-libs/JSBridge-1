class Request<T> {
    public static new<T>(uuid: string, service: string, param: T): Request<T> {
        return new Request(uuid, service, param);
    }
    constructor(readonly uuid: string, readonly service: string, readonly param: T) { }
}
class Response<T> {
    public static STATUS_SUCCESS = 0;
    public static STATUS_FAILED = 1;
    public static STATUS_CANCELED = 2;
    public static success<T>(msg: string = "success", result: T): Response<T> {
        return new Response<T>(this.STATUS_SUCCESS, msg, result);
    }
    public static failed(msg: string = "failed"): Response<any> {
        return new Response(this.STATUS_FAILED, msg, null);
    }
    public static canceled(msg: string = "canceled"): Response<any> {
        return new Response(this.STATUS_CANCELED, msg, null);
    }
    constructor(readonly code: number, readonly msg: string, readonly result: T) {
    }
}

export {
    Request,
    Response,
};
