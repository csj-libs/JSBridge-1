// can not ensure that all the code is executed after injected
let uniqueId = 1;
let callbacks = {};
let handlers = {};
let pendingCalls = [];
let pendingMsgs = [];

let registerJSHandler = (handlerName, handler) => {
    handlers[handlerName] = {
        handleNativeCall: (request) => {
            handler.handle(request, {
                success: (body = {}) => {
                    pivot.responseFromJS(request.callback_id, JSON.stringify({
                        status: 0,
                        msg: 'success',
                        body: body
                    }));
                },
                failed: (msg = "failed") => {
                    pivot.responseFromJS(request.callback_id, JSON.stringify({
                        status: 1,
                        msg: msg,
                        body: {}
                    }));
                },
                cancel: (reason = 'canceled') => {
                    pivot.responseFromJS(request.callback_id, JSON.stringify({
                        status: 2,
                        msg: reason,
                        body: {}
                    }))
                }
            })
        }
    };
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
            reject(e);
            delete callbacks[callbackId];
        }
    });
};

window.straw = {
    callNative: callNative,
    registerJSHandler: registerJSHandler
};

//called by native code
window.callFromNative = (request) => {
    let handlerName = request.handler_name;
    if (handlers[handlerName]) {
        handlers[handlerName].handleNativeCall(request);
    } else {
        console.log('undefined handler');
    }

};
//called by native code
window.responseFromNative = (callbackId, response) => {
    if (callbacks[callbackId]) {
        callbacks[callbackId].onResponse(response);
        delete callbacks[callbackId];
    }
};


const event = new Event('onStrawInit');

document.dispatchEvent(event);
