package com.xiansenliu.jstraw

import android.util.ArrayMap
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.xiansenliu.jstraw.callback.NativeCallback
import com.xiansenliu.jstraw.i.IJStraw
import com.xiansenliu.jstraw.i.IPivot
import com.xiansenliu.jstraw.i.NativeHandler
import com.xiansenliu.jstraw.msg.Request

/**
 * Created by xinliu on 10/23/17.
 */
class Pivot(private val wv: WebView, private val straw: IJStraw) : IPivot {

    private val handlers =
            LinkedHashMap<String, NativeHandler<*, *>>(10, 0.75f, true)
    private val callbacks = ArrayMap<String, NativeCallback<*>>()

    override fun transact(funname: String, data: String, callback: NativeCallback<*>?) {
        val callbackId = generateCallbackId(funname)
        if (callback != null) {
            callbacks.put(callbackId, callback)
        }
        val request = Request.create(funname, callbackId, data)
        val json = obj2Json(request)
        transact2JS(wv, json)
    }

    @JavascriptInterface
    override fun onTransact(funname: String, data: String) {
        val handler = handlers[funname] ?: UndefinedNativeHandler
        handler.handleJSCall(data, wv)
    }

    @JavascriptInterface
    override fun onResponse(callbackId: String, data: String) {
        callbacks.remove(callbackId)?.deliver(data)
    }

    override fun addHandler(handler: NativeHandler<*, *>) {
        handlers.put(handler.description(), handler)
    }

    override fun removeHandler(description: String) {
        handlers.remove(description)
    }
}
