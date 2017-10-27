window.pivot = {
    callFromJS: (handlerName, requestStr) => {
        let request = JSON.parse(requestStr);
        const response = {
            status: 0,
            msg: 'success',
            data: {}
        };
        responseFromNative(request.callback_id, JSON.stringify(response))
    }

};
