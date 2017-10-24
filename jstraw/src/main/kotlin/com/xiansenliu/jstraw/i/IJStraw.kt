package com.xiansenliu.jstraw.i

import com.xiansenliu.jstraw.callback.NativeCallback

/**
 * Author       xinliu
 * Date         10/22/17
 * Time         10:15 AM
 */
interface IJStraw {

    fun init()

    fun callJS(funname: String, data: String="", callback: NativeCallback<*>? = null)

    fun registerNativeHandler(handler: NativeHandler<*,*>)
    fun unregisterNativeHandler(description:String)
}