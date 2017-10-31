package com.xiansenliu.jstraw

import com.xiansenliu.jstraw.handler.NativeHandler

/**
 * Author       xinliu
 * Date         10/22/17
 * Time         10:15 AM
 */
abstract class IJStraw {


    abstract  fun <T> callJS(handlerName: String, data: String = ""): NativeCallback<T>

    abstract fun registerNativeHandler(handler: NativeHandler<*, *>)
    abstract internal fun findNativeHandler(handlerName: String): NativeHandler<*, *>?

    abstract internal fun addCallback(callbackId: Int, callback: NativeCallback<*>)
    abstract internal fun removeCallback(callbackId: Int): NativeCallback<*>?
}