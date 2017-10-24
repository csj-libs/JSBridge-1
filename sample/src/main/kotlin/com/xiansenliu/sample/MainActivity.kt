package com.xiansenliu.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import com.xiansenliu.jstraw.JStraw
import com.xiansenliu.jstraw.i.NativeHandler
import com.xiansenliu.jstraw.msg.Response


class MainActivity : AppCompatActivity() {
    lateinit var wv: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wv = WebView(this)
        setContentView(wv)
        val jStraw = JStraw.create(wv)
        jStraw.registerNativeHandler(object : NativeHandler<RequestModel, ResponseModel> {
            override fun description(): String = "Test"

            override fun handle(data: RequestModel): Response<ResponseModel> {
                return Response.success(ResponseModel())
            }

        })
    }
}

class RequestModel {}

class ResponseModel {}
