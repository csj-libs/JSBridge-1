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
    private var uniqueId = 0

    init {
        wv.addJavascriptInterface(this, "pivot")
    }

    override fun callJS(handlerName: String, params: String, callback: NativeCallback<*>?) {
        val callbackId = uniqueId++
        if (callback != null) {
            straw.addCallback(callbackId, callback)
        }
        val request = Request.create(handlerName, callbackId, params)
        callJS(wv, obj2Json(request))
    }

    @JavascriptInterface
    override fun callFromJS(handlerName: String, request: String) {
        val handler = straw.findNativeHandler(handlerName) ?: UndefinedNativeHandler
        handler.handleJSCall(request, wv)
    }

    @JavascriptInterface
    override fun responseFromJS(callbackId: Int, data: String) {
        straw.removeCallback(callbackId)?.deliver(data)
    }

}
