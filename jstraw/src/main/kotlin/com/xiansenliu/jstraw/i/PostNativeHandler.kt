package com.xiansenliu.jstraw.i

import android.os.Handler
import android.os.Looper
import android.webkit.WebView
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
    override fun handleJSCall(data: String, wv: WebView) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            val request = json2Obj<Request<T>>(data)
            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.post {
                val response = handle(request.params)
                response(wv, request, response)
            }
        } else {
            super.handleJSCall(data, wv)
        }
    }
}