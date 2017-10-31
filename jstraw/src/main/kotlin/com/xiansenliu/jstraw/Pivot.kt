package com.xiansenliu.jstraw

import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.xiansenliu.jstraw.handler.UndefinedNativeHandler
import com.xiansenliu.jstraw.msg.Request
import com.xiansenliu.jstraw.util.InteractUtil
import com.xiansenliu.jstraw.util.JsonUtil

/**
 * Author       xinliu
 * Date         10/23/17
 * Time
 */
class Pivot(private val wv: WebView, private val straw: IJStraw) : IPivot {
    private var uniqueId = 0
    private val TAG = "Pivot"

    init {
        wv.addJavascriptInterface(this, "pivot")
    }

    override fun callJS(handlerName: String, params: String, callback: NativeCallback<*>) {
        val callbackId = uniqueId++
        straw.addCallback(callbackId, callback)
        val request = Request.create(handlerName, callbackId, params)
        val requestStr = JsonUtil.obj2Json(request)

        InteractUtil.callJS(wv, requestStr)
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
