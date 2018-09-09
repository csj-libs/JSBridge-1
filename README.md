# JSBridge
JSBridge 的简单实现，Native 端使用 Kotlin、JS 端使用 Typescript 实现
支持双向通信



## Native 引入

```kotlin
val injector = object : BridgeJSInjector() {
    override fun jsContent(): String {
        // 这里替换成 bridgecore.js 内容
        // TODO: 这里入侵性更低的方式
        return "console.log('hello world');"
    }
}
wv.webViewClient = object : WebViewClient() {
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        injector.onPageFinished(view)
    }
}
bridge = BridgeImpl(wv)
```





## Native 注册服务
实现 `Service` 子类：
```kotlin
@ServiceName(name="toast")
class YourService : Service<Any, String>() {
    override fun handle(data: Any, callback: JSCallback<String>) {
        // ...
        callback.success("the result!")
    }
}

// 注册
Bridge.registerService(YourService())
```

这里要注意 `handle` 方法执行在子线程，如果要对 View 进行修改操作，需要切换线程



## JS 调用 Native 服务 

支持 `Promise` :

```javascript
// 便捷封装
function showToast(msg: string): Promise<string> {
    return getBridge().callNative<string, string>('toast', msg)
        .then((r) => {
            return Promise.resolve(r);
        });
}
// 调用
showToast("this is arg")
    .then((result) => {
    // tslint:disable-next-line:no-console
    console.log(result);
}).catch((e) => {
    // tslint:disable-next-line:no-console
    console.error(e);
});
```



## JS 注册服务

实现 `Service` 子类:

```typescript
class YourService extends Service<string, string> {
    
    public handle(param: string, callback: RemoteCallback<string>): void {
        callback.success(`the result!`);
    }
}

export YourService;

// 注册服务，TS 方式
((window as any)[BRIDGE] as Bridge).registerService(new YourService('js service'))
```



## Native 调用 JS 服务

```kotlin
bridge.callJS("js service", "this is the arg", object : NativeCallback<String>() {
    override fun success(result: String) {
        // ...
    }

    override fun failed(msg: String) {
        // ...
    }

    override fun canceled(msg: String) {
        // ...
    }
})
```

回调方法执行线程都在 non-UI 线程，所以要注意线程安全



## License 

```
                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/
   Copyright 2018 dashMrl

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


