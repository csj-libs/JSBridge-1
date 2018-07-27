package com.dashmrl.jsbridge

import android.os.Handler
import android.os.Looper
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.dashmrl.jsbridge.model.Request
import com.dashmrl.jsbridge.util.JSON

/**
 * Author       xinliu
 * Date         10/23/17
 * Time
 */
internal class Proxy(private val wv: WebView, private val listener: Listener) {
    private val TAG = "Proxy"

    init {
        wv.addJavascriptInterface(this, "bridge")
        wv.settings.javaScriptEnabled = true
    }

    fun request2JS(request: Request<String>) {
        val requestStr = JSON.stringify(request)
        Handler(Looper.getMainLooper()).post {
            wv.loadUrl("javascript:window.requestFromNative($requestStr)")
        }
    }

    @JavascriptInterface
    fun responseFromJS(uuid: String, response: String) {
        listener.onResponse(uuid, response)
    }

    @JavascriptInterface
    fun requestFromJS(service: String, request: String) {
        listener.onRequest(service, request, this)
    }

    fun response2JS(uuid: String, response: String) {
        Handler(Looper.getMainLooper()).post {
            wv.loadUrl("javascript:responseFromNative($uuid,$response)")
        }
    }

    internal interface Listener {
        fun onRequest(service: String, request: String, proxy: Proxy)
        fun onResponse(uuid: String, response: String)
    }
}
