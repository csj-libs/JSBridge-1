package com.xiansenliu.jstraw.i

import android.webkit.WebView
import com.xiansenliu.jstraw.json2Obj
import com.xiansenliu.jstraw.msg.Request
import com.xiansenliu.jstraw.msg.Response
import com.xiansenliu.jstraw.obj2Json
import com.xiansenliu.jstraw.response2JS

/**
 * Author       xinliu
 * Date         10/22/17
 * Time         10:31 AM
 * used to handle the call from JS environment
 */
interface NativeHandler<T, R> {
    fun description(): String
    fun handleJSCall(data: String, wv: WebView) {
        val request = json2Obj<Request<T>>(data)
        val response = handle(request.params)
        response(wv, request, response)
    }

    fun handle(data: T): Response<R>

    fun response(wv: WebView, request: Request<T>, response: Response<R>) {
        if (request.callbackId.isNotEmpty()) {
            response2JS(wv, request.callbackId, obj2Json(response))
        }
    }
}