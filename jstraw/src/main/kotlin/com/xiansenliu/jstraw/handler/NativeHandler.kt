package com.xiansenliu.jstraw.handler

import android.util.Log
import android.webkit.WebView
import com.xiansenliu.jstraw.JSCallback
import com.xiansenliu.jstraw.util.JsonUtil
import com.xiansenliu.jstraw.msg.Request

/**
 * Author       xinliu
 * Date         10/22/17
 * Time         10:31 AM
 * used to handle the call from JS environment
 */
interface NativeHandler<in T, out R> {
    fun name(): String
    fun handleJSCall(requestStr: String, wv: WebView) {
        Log.d("NativeHandler",requestStr)
        val request = JsonUtil.json2Obj<Request<T>>(requestStr)
        handle(request.params, JSCallback(wv, request.callbackId))
    }

    fun handle(data: T, callback: JSCallback<R>)

}