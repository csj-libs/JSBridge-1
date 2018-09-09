import React from "react";
import ReactDOM from "react-dom";
import UUID from "uuid/v4";
import { Request, Response } from "./core/models";
import { Proxy } from "./core/proxy";
import { showToast } from "./sdk/bridgesdk";
class FakeNativeProxy {
    public requestFromJS(reqStr: string) {
        const req: Request<any> = JSON.parse(reqStr);
        window.alert(`request from JS\n` +
            `uuid = ${req.uuid}\n` +
            `service = ${req.service}\n` +
            `params = ${JSON.stringify(req.param)}`);
        const res = Response.success("success", "complete");
        return ((window as any).LOCAL_PROXY as Proxy).responseFromNative(req.uuid, res);
    }
    public responseFromJS(uuid: string, resStr: string) {
        // tslint:disable-next-line:no-console
        console.log(`response from js: ${uuid}`);
        const res: Response<any> = JSON.parse(resStr);
        window.alert(`response from JS\n` +
            `uuid = ${uuid}\n` +
            `msg = ${res.msg}`);
    }
}

class Button extends React.PureComponent {
    public componentDidMount() {
        (window as any).REMOTE_PROXY = new FakeNativeProxy();

    }
    public render() {
        return (
            <div
                style={{
                    display: "flex",
                    flexDirection: "column",
                }}>

                <button onClick={() => {
                    this.handleRequestNative();
                }}
                    style={{
                        margin: "10px",
                    }}>
                    Mock requesting native toast</button>
                <button onClick={() => {
                    this.handleRequestJS();
                }}
                    style={{
                        margin: "10px",
                    }}>
                    Mock requesting js alert
                </button>
            </div>
        );
    }

    private handleRequestNative() {
        showToast("Toast...")
            .then((str) => {
                // tslint:disable-next-line:no-console
                console.log(str);
            }).catch((e) => {
                // tslint:disable-next-line:no-console
                console.error(e);
            });
    }
    private handleRequestJS() {
        const uuid = UUID();
        // tslint:disable-next-line:no-console
        console.log(`request to js: ${uuid}`);
        ((window as any).LOCAL_PROXY as Proxy)
            .requestFromNative("not found", new Request<string>(uuid, "target service", "params"));
    }
}

ReactDOM.render(<Button />, document.getElementById("root"));
