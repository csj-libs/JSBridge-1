package com.xiansenliu.jstraw

import android.annotation.SuppressLint
import android.util.SparseArray
import android.webkit.WebView
import android.webkit.WebViewClient
import com.xiansenliu.jstraw.handler.NativeHandler
import com.xiansenliu.jstraw.wv.JStrawWebViewClient

/**
 * Author       xinliu
 * Date         10/22/17
 * Time         10:15 AM
 */
@SuppressLint("SetJavaScriptEnabled")
class JStraw internal constructor(wv: WebView, wcc: WebViewClient?, jsUrl: String) : IJStraw() {
    private val pivot: IPivot = Pivot(wv, this)
    private val handlers = LinkedHashMap<String, NativeHandler<*, *>>(10, 0.75f, true)
    private val callbacks = SparseArray<NativeCallback<*>>()

    init {
        with(wv.settings) {
            javaScriptEnabled = true
        }
        wv.webViewClient = JStrawWebViewClient(wcc, jsUrl)
    }

    override fun <T> callJS(handlerName: String, data: String): NativeCallback<T> {
        return NativeCallback(pivot, handlerName, data)
    }

    override fun registerNativeHandler(handler: NativeHandler<*, *>) {
        handlers.put(handler.name(), handler)
    }

    override fun findNativeHandler(handlerName: String): NativeHandler<*, *>? {
        return handlers[handlerName]
    }

    override fun addCallback(callbackId: Int, callback: NativeCallback<*>) {
        callbacks.put(callbackId, callback)
    }

    override fun removeCallback(callbackId: Int): NativeCallback<*>? {
        val callback = callbacks[callbackId]
        callbacks.remove(callbackId)
        return callback
    }

}