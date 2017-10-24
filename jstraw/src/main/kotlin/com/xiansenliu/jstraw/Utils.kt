package com.xiansenliu.jstraw

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

internal fun transact2JS(wv: WebView, params: String) {
    wv.loadUrl("javascript:transact($params)")
}

internal fun response2JS(wv: WebView, params: String) {
    wv.loadUrl("javascript:onResponse($params)")

}