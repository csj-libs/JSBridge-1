# JStraw
A simple JSBridge for Android which helps with the interaction between Java and JavaScript

## How to use (in Kotlin)
> Communication is two-way : native serves js or js servers native 
### Native serves JS
#### Native side
- Step One. 
create your NativeHandler which will handle the message from JavaScript 
```kotlin
class HelloNativeHandler:NativeHandler<String, String> {
//  the returned value should be meaningful and it will be referenced in JavaScript
    override fun name(): String = "StringHandler"
//  this method is not run in main thread,but all the callback's methods will run in main thread.
    override fun handle(data: String, callback: JSCallback<String>) {
        toast("JS:$data")
        callback.success("I'm fine!!!")
    }
}
```

- Step Two.
Create an instance of JStraw with JStrawBuilder,and register your HelloNativeHandler.
 ```kotlin
val jStraw = JStrawBuilder(webview)
                .handler(HelloNativeHandler())
                .build()
```

#### JS Side
- Step One.
You should register a event listener,because the bridge may not be available when your web page is 
loaded, and all the service provided by native should be used after **onStrawInit** event is being 
triggered.
```javascript
if (window.straw) {
//  your work
} else {
    document.addEventListener('onStrawInit', (event) => {
//      your work
    });
}
```
- Step Two.
After the bridge's initialization is completed,you can call native for some service.
```javascript
straw.callNative('StringHandler','How are you?')
.then((res)=>{
//  parameter represents the result, and you should determine whether the call is succes 
//  by res.status ( 0 => ok/success, 1 => failed, 2 => canceled ).
    console.log(res.body)
}).catch((error)=>{
    console.log(error)
});
``` 
All your works done,enjoy it!!!


### JS serves Native
#### JS Side 
- Step One.
You should register a event listener,because the bridge may not be available when your web page is 
loaded, and all the service provided by native should be used after **onStrawInit** event is being 
triggered.
```javascript
if (window.straw) {
//  your work
} else {
    document.addEventListener('onStrawInit', (event) => {
//      your work
    });
}
```

- Step Two.
Register you JSHandler which will receives the message from native.
```javascript
//  the `JsHelloHandler` will be referenced in native,try to make it meaningful.
straw.registerJSHandler('JSHelloHandler',{
    handle:function(request,callback){
        console.log(request.params);
        callback.success('I\'m fine!!!');
    }
});
```

#### Native Side
- Step One.
Create an instance of JStraw,and load the web page.
```kotlin
val jstraw = JStrawBuilder(webview).build()
jstraw.loadUrl("url/of/the/page.html")
```

- Step Two.
Call JS
```kotlin
//  the generic type String indicates your expected returned data is a String instance.
jStraw.callJS<String>("JSHelloHandler", "Hello")
        .success { result: String -> toast(result) }
        .failed { msg -> toast(msg) }
        .canceled { toast("canceled") }
        .error { e -> toast(e.message.toString()) }
        .exec()
```
Enjoy it !


## The Ext Module (has not launched yet)
> This module provides some basic function for each others( js and native )

## How to Build
- Step One.
you should install `npm`,and enter the `JSLib`,then run command:
```shell
npm install --save-dev
npm build
```
> Note : if your platform is Windows,edit the `mv.sh` with Windows'batch command.

- Step Two.
build Android Project


## Some Problems
- it will caused some bad reaction if interaction is happening in the initializing period.
- it is very simple for JS to generate interface of native's services,but native has to
write some boring code even create a subclass of JStraw


## How to Integrate
> this library has not been upload to JitPack or Jcenter yet.

## License 

```
                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/
   Copyright 2017 xiansenLiu

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```


