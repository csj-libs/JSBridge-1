package com.xiansenliu.jstraw

import com.xiansenliu.jstraw.msg.Response
import com.xiansenliu.jstraw.util.JsonUtil

/**
 * Author       xinliu
 * Date         10/31/17
 * Time         7:19 PM
 */
class NativeCallback<T> internal constructor(private val pivot: IPivot,
                                             private val handlerName: String,
                                             private val params: String) {
    private var success: (T) -> Unit = {}
    private var failed: (String) -> Unit = {}
    private var canceled: () -> Unit = {}
    private var error: (Exception) -> Unit = {}
    fun success(success: (T) -> Unit): NativeCallback<T> {
        this.success = success
        return this
    }

    fun failed(failed: (String) -> Unit): NativeCallback<T> {
        this.failed = failed
        return this
    }

    fun canceled(canceled: () -> Unit): NativeCallback<T> {
        this.canceled = canceled
        return this
    }

    fun error(error: (Exception) -> Unit): NativeCallback<T> {
        this.error = error
        return this
    }

    fun exec() {
        try {
            pivot.callJS(handlerName, params, this)
        } catch (e: Exception) {
            error(e)
        }
    }

    internal fun deliver(data: String) {
        val response = JsonUtil.json2Obj<Response<T>>(data)
        when (response.status) {
            Response.STATUS_OK -> success(response.data)
            Response.STATUS_FAILED -> {
                failed(response.msg)
            }
            Response.STATUS_CANCEL -> canceled()
        }
    }

}