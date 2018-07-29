import Bridge from "../core/bridge";

/**
 * BridgeSDK
 * this class is used wrap native service in a convient way,
 * then developers can request native service by simplely
 * calling bridgesdk.servicemethod(param).then(r=>{})
 */
const nativeServices = {
    showToast: "toast",
};
function getBridge(): Bridge {
    return (window as any).bridge as Bridge;
}

// FIXME: this is a sample
function showToast(msg: string): Promise<string> {
    return getBridge().callNative<string, string>(nativeServices.showToast, msg);
}

export {
    showToast,
};
