package com.dashmrl.sample

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import com.dashmrl.jsbridge.Bridge
import com.dashmrl.jsbridge.BridgeImpl
import com.dashmrl.jsbridge.callback.JSCallback
import com.dashmrl.jsbridge.callback.NativeCallback
import com.dashmrl.jsbridge.service.Service
import com.dashmrl.jsbridge.service.ServiceName
import com.dashmrl.jsbridge.wv.BridgeJSInjector


class MainActivity : AppCompatActivity() {
    lateinit var wv: WebView
    lateinit var bridge: Bridge
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wv = WebView(this)
        setContentView(wv)
        val injector = object : BridgeJSInjector() {
            override fun jsContent(): String {
                return "console.log('hello world');"
            }
        }
        wv.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                injector.onPageFinished(view)
            }
        }
        bridge = BridgeImpl(wv)
        bridge.registerService(@ServiceName("test") object : Service<String, String>() {
            override fun handle(data: String, callback: JSCallback<String>) {

            }
        })
        wv.loadUrl("file:///android_asset/index.html")

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_call_js -> {
                bridge.callJS("test", "hello", object : NativeCallback<String>() {
                    override fun success(result: String) {
                    }

                    override fun failed(msg: String) {
                    }

                    override fun canceled(msg: String) {
                    }
                })
            }
        }
        return super.onOptionsItemSelected(item)
    }
}