package com.xiansenliu.jstraw.handler

import android.os.Handler
import android.os.Looper
import android.webkit.WebView
import com.xiansenliu.jstraw.JSCallback
import com.xiansenliu.jstraw.msg.Request
import com.xiansenliu.jstraw.util.JsonUtil

/**
 * Author       xinliu
 * Date         10/22/17
 * Time         10:31 AM
 * handle the call from JS environment in main Thread
 */
interface PostNativeHandler<T, R> : NativeHandler<T, R> {
    override fun handleJSCall(requestStr: String, wv: WebView) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            val request = JsonUtil.json2Obj<Request<T>>(requestStr)
            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.post {
                handle(request.params, JSCallback(wv, request.callbackId))
            }
        } else {
            super.handleJSCall(requestStr, wv)
        }
    }
}