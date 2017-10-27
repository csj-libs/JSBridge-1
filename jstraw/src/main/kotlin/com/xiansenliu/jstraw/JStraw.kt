package com.xiansenliu.jstraw

import android.support.annotation.MainThread
import android.util.ArrayMap
import android.util.SparseArray
import android.webkit.WebView
import com.xiansenliu.jstraw.i.IJStraw
import com.xiansenliu.jstraw.i.IPivot
import com.xiansenliu.jstraw.i.NativeHandler
import com.xiansenliu.jstraw.callback.NativeCallback

/**
 * Author       xinliu
 * Date         10/22/17
 * Time         10:15 AM
 */
class JStraw internal constructor(wv: WebView) : IJStraw() {
    private val pivot: IPivot = Pivot(wv, this)

    private val handlers =
            LinkedHashMap<String, NativeHandler<*, *>>(10, 0.75f, true)
    private val callbacks = SparseArray<NativeCallback<*>>()

    override fun callJS(handlerName: String, data: String, callback: NativeCallback<*>?) {
        pivot.callJS(handlerName, data, callback)
    }

    fun callJSFun(handlerName: String, data: Any, callback: NativeCallback<*>?) {
        callJS(handlerName, obj2Json(data), callback)
    }

    override fun registerNativeHandler(handler: NativeHandler<*, *>) {
        handlers.put(handler.description(), handler)
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

    companion object {
        val TAG = JStraw::class.java.simpleName
        @MainThread
        fun create(wv: WebView): IJStraw = JStraw(wv)
    }

}