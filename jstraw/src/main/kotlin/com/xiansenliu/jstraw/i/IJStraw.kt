package com.xiansenliu.jstraw.i

import com.xiansenliu.jstraw.callback.NativeCallback

/**
 * Author       xinliu
 * Date         10/22/17
 * Time         10:15 AM
 */
interface IJStraw {


    fun callJS(handlerName: String, data: String = "{}", callback: NativeCallback<*>? = null)

    fun registerNativeHandler(handler: NativeHandler<*, *>)
    fun findNativeHandler(handlerName: String): NativeHandler<*, *>?

    fun addCallback(callbackId: String, callback: NativeCallback<*>)
    fun removeCallback(callbackId: String): NativeCallback<*>?
}