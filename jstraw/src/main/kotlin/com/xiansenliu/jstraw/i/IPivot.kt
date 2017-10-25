package com.xiansenliu.jstraw.i

import com.xiansenliu.jstraw.callback.NativeCallback

/**
 * Author       xinliu
 * Date         10/22/17
 * Time         10:15 AM
 *
 */
internal interface IPivot {
    /**
     * this method is used to call JS function
     * */
    fun callJS(handlerName: String, data: String, callback: NativeCallback<*>?)

    /**
     * this method will received JS's call
     * */
    fun callFromJS(handlerName: String, data: String)

    /**
     * used to receive response from JS
     * */
    fun responseFromJS(callbackId: String, data: String)

}