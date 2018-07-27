package com.dashmrl.jsbridge.model

/**
 * Created by xinliu on 10/24/17.
 * Model that represents the request 2 and from JS
 */
data class Request<T>(
        val uuid: String,
        val service: String,
        val param: T
) {
    companion object {
        fun <T> new(uuid: String, service: String, param: T): Request<T> {
            return Request(uuid, service, param)
        }
    }
}