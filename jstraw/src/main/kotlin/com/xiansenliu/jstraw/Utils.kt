package com.xiansenliu.jstraw

import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.webkit.URLUtil
import android.webkit.WebResourceResponse
import android.webkit.WebView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileInputStream

/**
 * Created by xinliu on 10/23/17.
 */

internal val gson = Gson()
internal const val SCHEMA = "https://"
internal const val ASSETS_AUTHORITY = "android_assets"
internal const val FILES_AUTHORITY = "android_files"

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

internal fun parseJSUrl(jsUrl: String): String {
    var authority = ""
    var path = ""
    if (URLUtil.isAssetUrl(jsUrl)) {
        authority = ASSETS_AUTHORITY
        path = Uri.parse(jsUrl).path
    } else if (URLUtil.isHttpUrl(jsUrl) || URLUtil.isHttpsUrl(jsUrl)) {
        return jsUrl
    } else if (File(jsUrl).exists()) {
        authority = FILES_AUTHORITY
        path = jsUrl.drop(0)
    }
    return Uri.Builder()
            .scheme(SCHEMA)
            .authority(authority)
            .path(path)
            .build()
            .toString()
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

internal fun loadLocalJS(wv: WebView, jsUrl: String): WebResourceResponse? {
    val uri = Uri.parse(jsUrl)
    if (uri.authority == FILES_AUTHORITY) {
        return WebResourceResponse("text/javascript", "UTF-8",
                FileInputStream(File(File.pathSeparator + uri.path)))
    } else if (uri.authority == ASSETS_AUTHORITY) {
        return WebResourceResponse("text/javascript", "UTF-8",
                wv.context.assets.open(uri.path))
    } else {
        return null
    }
}