package com.xiansenliu.jstraw.callback

import com.xiansenliu.jstraw.json2Obj
import com.xiansenliu.jstraw.msg.Response

/**
 * Created by xinliu on 10/23/17.
 * handle the response from JS
 */
abstract class NativeCallback<T> {

    internal fun deliver(data: String) {
        val response = json2Obj<Response<T>>(data)
        when (response.status) {
            Response.STATUS_OK -> onSuccess(response.data)
            Response.STATUS_FAILED -> onFailed(response.msg)
            Response.STATUS_CANCEL -> onCancel()
        }
    }

    open fun onSuccess(data: T) {}

    open fun onFailed(msg: String) {}

    open fun onCancel() {}
}