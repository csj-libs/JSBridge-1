package com.xiansenliu.jstraw

import com.xiansenliu.jstraw.NativeCallback

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
    fun callJS(handlerName: String, params: String, callback: NativeCallback<*>)

    /**
     * this method will received JS's call
     * */
    fun callFromJS(handlerName: String, request: String)

    /**
     * used to receive response from JS
     * */
    fun responseFromJS(callbackId: Int, data: String)

}