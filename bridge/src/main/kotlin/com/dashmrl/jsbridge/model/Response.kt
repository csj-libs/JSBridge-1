package com.dashmrl.jsbridge.model

/**
 * Author       xinliu
 * Date         10/24/17
 * Time
 * Model that represents the response send 2 and from JS
 */
data class Response<out T>(
        val code: Int,
        val msg: String,
        val result: T
) {
    companion object {
        internal const val STATUS_SUCCESS = 0
        internal const val STATUS_FAILED = 1
        internal const val STATUS_CANCELED = 2
        fun <T> success(msg: String = "success", data: T): Response<T> = Response(STATUS_SUCCESS, msg, data)
        fun cancel(msg: String = "canceled"): Response<Unit> = Response(STATUS_CANCELED, msg, Unit)
        fun failed(msg: String = "failed"): Response<Unit> = Response(STATUS_FAILED, msg, Unit)
    }
}