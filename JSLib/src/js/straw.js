let uniqueId = 1;
let callbacks = {};
let handlers = {};
let pendingCalls = [];
let pendingMsgs = [];

let registerJSHandler = (handlerName, handler) => {
    handlers[handlerName] = handler;
};
let callNative = (handlerName, params = {}) => {
    let callbackId = uniqueId++;
    const request = {
        handler_name: handlerName,
        callback_id: callbackId,
        params: params
    };
    return new Promise((resolve, reject) => {
        try {
            callbacks[callbackId] = {
                onResponse: (response) => {
                    resolve(response);
                }
            };
            pivot.callFromJS(handlerName, JSON.stringify(request));
        } catch (e) {
            reject(e)
        }
    });
};

window.straw = {
    callNative: callNative,
    registerJSHandler: registerJSHandler
};

//called by native code
window.callFromNative = (handlerName, request) => {
    if (handlers[handlerName]) {
        handlers[handlerName].handleNativeCall(request)
    }
};
//called by native code
window.responseFromNative = (callbackId, response) => {
    if (callbacks[callbackId]) {
        callbacks[callbackId].onResponse(JSON.parse(response));
        delete callbacks[callbackId];
    }
};

let count=1;


