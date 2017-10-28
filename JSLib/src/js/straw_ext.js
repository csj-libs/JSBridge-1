const nativeApiList = [{
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
        straw[`${nativeApi.handlerName}`] = apiGenerator(nativeApi);
    });
    // for (var i in list) {
    //   const nativeApi = list[i];
    // }
};
// TODO this method should be called after straw.bundle.js id loaded
if (window.straw) {
    apiFactory(nativeApiList);
} else {
    document.addEventListener('onStrawInit', (event) => {
        console.log('Straw inited');
        apiFactory(nativeApiList);
    });
}
// window.prod = () => {
//   console.log(nativeApiList);
//
// };
