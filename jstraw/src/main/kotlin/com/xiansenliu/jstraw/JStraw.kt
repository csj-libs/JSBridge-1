package com.xiansenliu.jstraw

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
class JStraw internal constructor(wv: WebView) : IJStraw {
    private val pivot: IPivot = Pivot(wv, this)

    override fun init() {

    }

    override fun callJSFun(identity: String, data: String, callback: NativeCallback<*>?) {
        pivot.transact(identity, data,callback)
    }

    fun callJSFun(identity: String, data: Any, callback: NativeCallback<*>?) {
        callJSFun(identity, obj2Json(data), callback)
    }

    override fun registerNativeHandler(handler: NativeHandler<*>) {
        pivot.addHandler(handler)
    }

    override fun unregisterNativeHandler(description: String) {
        pivot.removeHandler(description)
    }

    companion object {
        val TAG = JStraw::class.java.simpleName
        fun create(wv: WebView): IJStraw = JStraw(wv)
    }

}