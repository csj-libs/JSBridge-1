package com.xiansenliu.jstraw.util

import android.net.Uri
import android.util.Log
import android.webkit.URLUtil
import android.webkit.WebResourceResponse
import android.webkit.WebView
import java.io.File
import java.io.FileInputStream

/**
 * Author       xinliu
 * Date         10/29/17
 * Time         4:42 PM
 */
class InjectUtil {
    companion object {
        private const val SCHEMA = "https"
        private const val ASSETS_AUTHORITY = "android_asset"
        private const val FILES_AUTHORITY = "android_files"

        internal fun parseJSUrl(jsUrl: String): String {
            Log.d("Utils", jsUrl)
            var authority = ""
            var path = ""
            if (URLUtil.isAssetUrl(jsUrl)) {
                authority = ASSETS_AUTHORITY
                path = Uri.parse(jsUrl).path.removePrefix("/android_asset/")
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

        internal fun loadLocalJS(wv: WebView, jsUrl: String): WebResourceResponse? {
            val uri = Uri.parse(jsUrl)
            if (uri.authority == FILES_AUTHORITY) {
                return WebResourceResponse("text/javascript", "UTF-8",
                        FileInputStream(File(File.separator + uri.path)))
            } else if (uri.authority == ASSETS_AUTHORITY) {
                return WebResourceResponse("text/javascript", "UTF-8",
                        wv.context.assets.open(uri.path.removePrefix(File.separator)))
            } else {
                return null
            }
        }

        internal fun injectJS(wv: WebView?, jsUrl: String) {
            if (wv != null) {
                val js = "var script = document.createElement('script');" +
                        "script.src = '$jsUrl';" +
                        "var firstScript = document.scripts[0];" +
                        "firstScript.parentNode.insertBefore(script,firstScript);"
                wv.loadUrl("javascript:$js")
                Log.d("injectJS", jsUrl)
            }
        }
    }
}