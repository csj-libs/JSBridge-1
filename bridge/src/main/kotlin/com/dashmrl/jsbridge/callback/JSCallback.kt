package com.dashmrl.jsbridge.callback

import com.dashmrl.jsbridge.Proxy
import com.dashmrl.jsbridge.model.Response
import com.dashmrl.jsbridge.util.JSON

/**
 * Author       xinliu
 * Date         10/28/17
 * Time         1:54 PM
 * used to send feedback to JS
 */
class JSCallback<in R> internal constructor(private val proxy: Proxy, private val uuid: String) {
    fun success(body: R) {
        send(Response.success(data = body))
    }

    fun failed(msg: String = "failed") {
        send(Response.failed(msg))
    }

    fun canceled(msg: String = "canceled") {
        send(Response.cancel())
    }

    private fun send(res: Response<*>) {
        proxy.response2JS(uuid, JSON.stringify(res))
    }
}