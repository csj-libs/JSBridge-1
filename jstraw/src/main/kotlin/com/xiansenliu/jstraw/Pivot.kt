package com.xiansenliu.jstraw

import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.xiansenliu.jstraw.callback.NativeCallback
import com.xiansenliu.jstraw.i.IJStraw
import com.xiansenliu.jstraw.i.IPivot
import com.xiansenliu.jstraw.msg.Request

/**
 * Created by xinliu on 10/23/17.
 */
class Pivot(private val wv: WebView, private val straw: IJStraw) : IPivot {
    override fun callJS(handlerName: String, data: String, callback: NativeCallback<*>?) {
        val callbackId = generateCallbackId(handlerName)
        if (callback != null) {
            straw.addCallback(callbackId, callback)
        }
        val request = Request.create(handlerName, callbackId, data)
        callJS(wv, obj2Json(request))
    }

    @JavascriptInterface
    override fun callFromJS(handlerName: String, data: String) {
        val handler = straw.findNativeHandler(handlerName) ?: UndefinedNativeHandler
        handler.handleJSCall(data, wv)
    }

    @JavascriptInterface
    override fun responseFromJS(callbackId: String, data: String) {
        straw.removeCallback(callbackId)?.deliver(data)
    }

}
