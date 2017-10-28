package com.xiansenliu.jstraw

import android.os.Handler
import android.os.Looper
import android.webkit.WebView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * Created by xinliu on 10/23/17.
 */

internal val gson = Gson()

internal fun <T> json2Obj(json: String): T {
    return gson.fromJson<T>(json, object : TypeToken<T>() {}.type)
}

internal fun obj2Json(obj: Any): String {
    return gson.toJson(obj)
}

internal fun callJS(wv: WebView, params: String) {
    execOnMain { wv.loadUrl("javascript:pivot.callFromNative($params)") }
}

internal fun response2JS(wv: WebView, callbackId: Int, params: String) {
    execOnMain { wv.loadUrl("javascript:pivot.responseFromNative($callbackId,$params)") }
}

internal inline fun execOnMain(crossinline f: () -> Unit) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        f()
    } else {
        Handler(Looper.getMainLooper()).post {
            f()
        }
    }
}

internal fun injectJS(wv: WebView?, jsUrl: String) {
    if (wv != null) {

        val js = "var script = document.createElement('script');" +
                "script.src = '$jsUrl';" +
                "var firstScript = document.scripts[0];" +
                "firstScript.parentNode.insertBefore(script,firstScript);"
        wv.loadUrl("javascript:$js")
    }
}