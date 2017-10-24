package com.xiansenliu.jstraw.msg

import com.google.gson.annotations.SerializedName

/**
 * Created by xinliu on 10/24/17.
 */
data class Request<T>(
        @SerializedName("call_id")
        val callId: String,
        @SerializedName("callback_id")
        val callbackId: String,
        @SerializedName("params")
        val params: T
) {
    companion object {
        fun <T> create(callId: String, callbackId: String, params: T): Request<T> {
            return Request(callId, callbackId, params)
        }
    }
}