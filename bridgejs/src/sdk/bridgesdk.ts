import Bridge from "../core/bridge";

/**
 * BridgeSDK
 * this class is used wrap native service in a convient way,
 * then developers can request native service by simplely
 * calling bridgesdk.servicemethod(param).then(r=>{})
 */
const BRIDGE = "BRIDGE";
const nativeServices = {
    showToast: "toast",
};
function getBridge(): Bridge {
    if (!(window as any)[BRIDGE]) {
        (window as any)[BRIDGE] = new Bridge();
    }
    return (window as any)[BRIDGE] as Bridge;
}

function showToast(msg: string): Promise<string> {
    return getBridge().callNative<string, string>(nativeServices.showToast, msg)
        .then((r) => {
            return Promise.resolve(r);
        });
}

export {
    showToast,
};
