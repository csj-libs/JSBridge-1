package com.dashmrl.jsbridge.wv

import android.webkit.WebView
import android.webkit.WebViewClient
import java.io.File

/**
 * Author       xinliu
 * Date         10/22/17
 * Time         10:24 AM
 */
open class BridgeJSInjector {
    fun onPageFinished(view: WebView?) {
        injectJS(view)
    }

    private fun injectJS(wv: WebView?) {
        if (wv == null) return
        when {
            jsUrl().isNotBlank() -> {
                val jsNode = "var script = document.createElement('script');" +
                        "script.src = '${jsUrl()}';" +
                        "var firstScript = document.scripts[0];" +
                        "firstScript.parentNode.insertBefore(script,firstScript);"
                wv.loadUrl("javascript:$jsNode")
            }
            jsPath().isNotBlank() -> {
                val jsNode = "var script = document.createElement('script');" +
                        "script.innerHTML = '${File(jsPath()).readText()}';" +
                        "var firstScript = document.scripts[0];" +
                        "firstScript.parentNode.insertBefore(script,firstScript);"
                wv.loadUrl("javascript:$jsNode")
            }
            jsContent().isNotBlank() -> {
                val jsNode = "var script = document.createElement('script');" +
                        "script.innerHTML = '${jsContent()}';" +
                        "var firstScript = document.scripts[0];" +
                        "firstScript.parentNode.insertBefore(script,firstScript);"
                wv.loadUrl("javascript:$jsNode")
            }
        }
    }

    open fun jsUrl(): String = ""
    open fun jsPath(): String = ""
    open fun jsContent(): String = ""
}