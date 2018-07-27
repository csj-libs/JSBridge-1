package com.dashmrl.jsbridge.callback

import com.dashmrl.jsbridge.model.Response
import com.dashmrl.jsbridge.util.JSON

/**
 * Author       xinliu
 * Date         10/31/17
 * Time         7:19 PM
 */
abstract class NativeCallback<T> {

    internal fun delivery(resStr: String) {
        val res = JSON.parse<Response<T>>(resStr)
        when (res.code) {
            Response.STATUS_SUCCESS -> {
                success(res.result)
            }
            Response.STATUS_FAILED -> {
                failed(res.msg)
            }
            Response.STATUS_CANCELED -> {
                canceled(res.msg)
            }
        }
    }

    open fun success(result: T) {
    }

    open fun failed(msg: String) {
    }

    open fun canceled(msg: String) {
    }
}