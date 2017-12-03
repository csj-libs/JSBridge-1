package com.xiansenliu.sample

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import com.xiansenliu.jstraw.JStrawBuilder
import com.xiansenliu.jstraw.JSCallback
import com.xiansenliu.jstraw.IJStraw
import com.xiansenliu.jstraw.handler.NativeHandler
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity() {
    lateinit var wv: WebView
    lateinit var jStraw: IJStraw
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wv = WebView(this)
        setContentView(wv)
        jStraw = JStrawBuilder(wv)
//                register a handler
                .handler(object : NativeHandler<String, String> {
                    override fun name(): String = "StringHandler"

                    override fun handle(data: String, callback: JSCallback<String>) {
                        toast("JS:$data")
                        callback.success("I'm fine!!!")
                    }
                })
                .build()
        wv.loadUrl("file:///android_asset/index.html")

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_call_js -> {
                jStraw.callJS<String>("StringHandler", "Hello")
                        .success { result: String -> toast(result) }
                        .failed { msg -> toast(msg) }
                        .canceled { toast("canceled") }
                        .error { e -> toast(e.message.toString()) }
                        .exec()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}