package com.xiansenliu.jstraw

import android.webkit.WebView
import com.xiansenliu.jstraw.msg.Response
import com.xiansenliu.jstraw.util.InteractUtil
import com.xiansenliu.jstraw.util.JsonUtil
import java.lang.ref.WeakReference

/**
 * Author       xinliu
 * Date         10/28/17
 * Time         1:54 PM
 * used to send feedback to JS
 */
class JSCallback<in R>(webView: WebView, private val callbackId: Int) {
    private val wv: WeakReference<WebView> = WeakReference(webView)

    fun success(body: R) {
        invoke(Response.success(data = body))
    }

    // if the js side do not need data returned , called this method
    fun success() {
        invoke(Response.success(data = Unit))
    }

    fun failed(msg: String = "failed") {
        invoke(Response.failed(msg))
    }

    fun cancel(msg: String = "canceled") {
        invoke(Response.cancel())
    }

    private fun invoke(response: Response<*>) {
        if (wv.get() != null) {
            InteractUtil.response2JS(wv.get()!!, callbackId, JsonUtil.obj2Json(response))
            wv.clear()
        }
    }
}