package com.xiansenliu.jstraw.msg

import com.google.gson.annotations.SerializedName

/**
 * Created by xinliu on 10/24/17.
 * Model that represents the response send 2 and from JS
 */
data class Response<T>(
        @SerializedName("status")
        val status: Int,
        @SerializedName("msg")
        val msg: String,
        @SerializedName("body")
        val data: T
) {
    companion object {
        internal val STATUS_OK = 0
        internal val STATUS_FAILED = 1
        internal val STATUS_CANCEL = 2
        fun <T> success(msg: String = "success", data: T): Response<T> = Response(STATUS_OK, msg, data)
        fun cancel(msg: String = "canceled"): Response<Unit> = Response(STATUS_CANCEL, msg, Unit)
        fun failed(msg: String = "failed"): Response<Unit> = Response(STATUS_FAILED, msg, Unit)
    }
}