package com.xiansenliu.jstraw

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.xiansenliu.jstraw.i.IJStraw
import com.xiansenliu.jstraw.i.NativeHandler

/**
 * Author       xinliu
 * Date         10/28/17
 * Time         2:45 PM
 */
class JStrawBuilder(private val wv: WebView) {
    private var straw_js = ""
    private val handlers =
            LinkedHashMap<String, NativeHandler<*, *>>(10, 0.75f, true)
    private var webViewClient: WebViewClient? = null

    fun webviewClient(wvc: WebViewClient = WebViewClient()): JStrawBuilder {
        this.webViewClient = wvc
        return this
    }

    fun handler(vararg handlers: NativeHandler<*, *>): JStrawBuilder {
        handlers.forEach { this.handlers.put(it.name(), it) }
        return this
    }

    fun strawJavaScript(): JStrawBuilder {
        this.straw_js
        return this
    }

    fun build(): IJStraw {
        val jStraw = JStraw(wv, webViewClient ?: WebViewClient())
        handlers.values.forEach { jStraw.registerNativeHandler(it) }

        return jStraw
    }

}