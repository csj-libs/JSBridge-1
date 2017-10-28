(function(){
    window.injected = function(){
    console.log('injected method called');
    return "injected";
}
    window.bridge.handleJSCall('hello')
})()

