const nativeApiList = [{
    "funName":"isLogin",
    "handlerName": "isLogin",
    "needParams": false
}];

let apiGenerator = (nativeApi) => {
    if (nativeApi.needParams) {
        return (params) => {
            return straw.callNative(nativeApi.handlerName, params);
        };
    } else {
        return () => {
            return straw.callNative(nativeApi.handlerName);
        };
    }
};

let apiFactory = (list) => {
    list.forEach((nativeApi) => {
        straw[`${nativeApi.funName}`] = apiGenerator(nativeApi);
    });
};
//function apiFactory() below should be called after straw.bundle-x.x.x.js is loaded
if (window.straw) {
    apiFactory(nativeApiList);
} else {
    document.addEventListener('onStrawInit', (event) => {
        console.log('Straw inited');
        apiFactory(nativeApiList);
    });
}
