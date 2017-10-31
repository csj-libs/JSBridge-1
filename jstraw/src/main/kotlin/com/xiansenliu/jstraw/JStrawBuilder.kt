package com.xiansenliu.jstraw

import android.webkit.WebView
import android.webkit.WebViewClient
import com.xiansenliu.jstraw.handler.NativeHandler
import com.xiansenliu.jstraw.util.InjectUtil

/**
 * Author       xinliu
 * Date         10/28/17
 * Time         2:45 PM
 */
class JStrawBuilder(private val wv: WebView) {
    private var jsUrl = InjectUtil.parseJSUrl("file:///android_asset/straw.bundle-1.0.0.js")
    private val handlers =
            LinkedHashMap<String, NativeHandler<*, *>>(10, 0.75f, true)
    private var webViewClient: WebViewClient? = null

    /**
     * if you'd like to set your custom WebViewClient,do it by calling
     * this method,or the target JS will not be injected.
     * */
    fun webviewClient(wvc: WebViewClient = WebViewClient()): JStrawBuilder {
        this.webViewClient = wvc
        return this
    }

    fun handler(vararg handlers: NativeHandler<*, *>): JStrawBuilder {
        handlers.forEach { this.handlers.put(it.name(), it) }
        return this
    }

    /**
     * @param jsUrl the js file that you want to inject into the current page,
     *              it looks like "file:///android_asset/your/own/jsfile.js" or
     *              "/path/to/your/own/jsfile.js" and even a remote link like
     *              "https://authority/path/to/your/jsfile.js".There maybe some
     *              mistake if you remote js file is over http rather than https
     * */
    fun strawJavaScript(jsUrl: String): JStrawBuilder {
        this.jsUrl = InjectUtil.parseJSUrl(jsUrl)
        return this
    }

    fun build(): IJStraw {
        val jStraw = JStraw(wv, webViewClient, jsUrl)
        handlers.values.forEach { jStraw.registerNativeHandler(it) }
        return jStraw
    }

}