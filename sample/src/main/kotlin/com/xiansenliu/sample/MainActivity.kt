package com.xiansenliu.sample

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Window
import android.webkit.*
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    lateinit var wv: WebView
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wv = WebView(this)
        setContentView(wv)
        with(wv.settings) {
            javaScriptEnabled = true
            allowContentAccess = true
            allowFileAccess = true
            allowFileAccessFromFileURLs = true
            allowUniversalAccessFromFileURLs = true
            domStorageEnabled = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }
        wv.addJavascriptInterface(Bridge(), "bridge")
        wv.loadUrl("https://tech.meituan.com")
//        wv.loadUrl("file:///android_asset/index.html")
        wv.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                if (view != null) {
//                    injectJS(view, "file:///android_asset/test.js")
                    injectJS(view, "test.js")
                }
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
                if (view == null || request == null) {
                    return super.shouldInterceptRequest(view, request)
                }
//                val url = request.url
//                Log.i("shouldInterceptRequest",url.path)
//                if (url.host == "android_asset") {
//                    val inputStream = this@MainActivity.assets.open("test.js")
//                    return WebResourceResponse("text/javascript", "UTF-8", inputStream)
//                }
                return super.shouldInterceptRequest(view, request)
            }
        }
    }

    fun injectJS(wv: WebView, jsUrl: String) {
        val js = "var script = document.createElement('script');" +
                "script.src = '$jsUrl';" +
                "var firstScript = document.scripts[0];" +
                "firstScript.parentNode.insertBefore(script,firstScript);"
        wv.loadUrl("javascript:$js")


        wv.postDelayed({ wv.loadUrl("javascript:console.log(window.injected);") }, 2000)
    }
}

class Bridge {
    @JavascriptInterface
    fun handleJSCall(msg: String) {
        Log.i("Bridge", msg)
    }
}