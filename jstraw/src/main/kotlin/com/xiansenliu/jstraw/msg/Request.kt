package com.xiansenliu.jstraw.msg

import com.google.gson.annotations.SerializedName

/**
 * Created by xinliu on 10/24/17.
 * Model that represents the request 2 and from JS
 */
data class Request<T>(
        @SerializedName("handler_name")
        val handlerName: String,
        @SerializedName("callback_id")
        val callbackId: Int,
        @SerializedName("params")
        val params: T
) {
    companion object {
        fun <T> create(callId: String, callbackId: Int, params: T): Request<T> {
            return Request(callId, callbackId, params)
        }
    }
}