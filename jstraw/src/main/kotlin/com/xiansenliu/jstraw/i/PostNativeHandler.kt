package com.xiansenliu.jstraw.i

import android.os.Handler
import android.os.Looper
import android.webkit.WebView
import com.xiansenliu.jstraw.callback.JSCallback
import com.xiansenliu.jstraw.json2Obj
import com.xiansenliu.jstraw.msg.Request
import com.xiansenliu.jstraw.obj2Json
import com.xiansenliu.jstraw.response2JS

/**
 * Author       xinliu
 * Date         10/22/17
 * Time         10:31 AM
 * handle the call from JS environment in main Thread
 */
interface PostNativeHandler<T, R> : NativeHandler<T, R> {
    override fun handleJSCall(requestStr: String, wv: WebView) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            val request = json2Obj<Request<T>>(requestStr)
            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.post {
                val response = handle(request.params, JSCallback(wv, request.callbackId))
                response2JS(wv, request.callbackId, obj2Json(response))
            }
        } else {
            super.handleJSCall(requestStr, wv)
        }
    }
}