package com.xiansenliu.sample

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView


class MainActivity : AppCompatActivity() {
    lateinit var wv: WebView
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wv = WebView(this)
        setContentView(wv)
        wv.settings.javaScriptEnabled = true
        wv.addJavascriptInterface(Bridge(), "bridge")
        wv.loadUrl("file:///android_asset/index.html")
    }
}


class Bridge {
    @JavascriptInterface
    fun handleJSCall(msg: String) {
        Log.d("Bridge", msg)
        Thread.sleep(3000)

    }
}