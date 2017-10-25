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

internal fun generateCallbackId(name: String): String {
    val uuid = UUID.fromString(name)
    return uuid.toString().replace("-", "")
}

internal fun callJS(wv: WebView, params: String) {
    execOnMain { wv.loadUrl("javascript:pivot.callFromJS($params)") }
}

internal fun response2JS(wv: WebView, callbackId: String, params: String) {
    execOnMain { wv.loadUrl("javascript:pivot.responseFromJS($callbackId,$params)") }
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

