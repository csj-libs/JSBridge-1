(function () {
    var callbacks = {};
    var handlers = {};
    if (!window.pivot) {
        return;
    }
    var pivot = window.pivot;
    var callbackId = 0;

    function generateCallbackId() {
        return callbackId++;
    }

    function wrapCallback(innerCallback) {
        return {
            deliver: function (response) {
                switch (response.status) {
                    case 0: {
                        innerCallback.success(response.data);
                        innerCallback.complete();
                        break;
                    }
                    case 1: {
                        innerCallback.failed(response.msg);
                        innerCallback.complete();
                        break;
                    }
                    case 2: {
                        innerCallback.cancel();
                        break;
                    }
                }
            }
        }
    }


    function callJava(funname, data, callback) {
        transact(funname, data, wrapCallback(callback));
    }

    function wrapHandler(innerHandler) {
        return {
            handleCall: innerHandler.handleCall,
            handleJavaCall: function (request, pivot) {
                var response = handleCall(request.params);
                if (request.callbackId !== "") {
                    pivot.onResponse(request.callbackId, JSON.stringify(response))
                }
            }
        }
    }


    function registerJSHandler(description, handler) {
        handlers[description] = wrapHandler(handler);
    }

    function unregisterHandler(description) {
        handlers[description] = null;
    }

    /*
    * call Java function
    * */
    function transact(funname, data, callback) {
        var callbackId = generateCallbackId();
        if (callback) {
            callbacks[callbackId] = callback;
        } else {
            callbackId = ""
        }

        pivot.onTransact(funname, JSON.stringify({
            funname: funname,
            callback_id: callbackId,
            params: data || ""
        }));
    }

    /*
    * handle call from Java
    * */
    function onTransact(data) {
        var request = JSON.parse(data);
        handlers[request.funname].handleJavaCall(request, pivot);
    }

    /*
    * handle feedback from Java
    * */
    function onResponse(callbackId, response) {
        callbacks[callbackId].deliver(JSON.parse(response));
        callbacks[callbackId] = null;
    }


    var jstraw = window.jstraw = {
        callJava: callJava,
        registerJSHandler: registerJSHandler,
        unregisterHandler: unregisterHandler
    }
})();
