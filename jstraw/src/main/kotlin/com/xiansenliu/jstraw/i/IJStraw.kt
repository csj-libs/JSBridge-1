package com.xiansenliu.jstraw.i

import com.xiansenliu.jstraw.callback.NativeCallback

/**
 * Author       xinliu
 * Date         10/22/17
 * Time         10:15 AM
 */
abstract class IJStraw {


    abstract fun callJS(handlerName: String, data: String = "{}", callback: NativeCallback<*>? = null)

    abstract fun registerNativeHandler(handler: NativeHandler<*, *>)
    abstract internal fun findNativeHandler(handlerName: String): NativeHandler<*, *>?

    abstract internal fun addCallback(callbackId: Int, callback: NativeCallback<*>)
    abstract internal fun removeCallback(callbackId: Int): NativeCallback<*>?
}