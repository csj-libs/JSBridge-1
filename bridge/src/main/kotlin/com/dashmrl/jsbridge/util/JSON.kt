package com.dashmrl.jsbridge.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Author       xinliu
 * Date         10/29/17
 * Time         4:53 PM
 */
internal class JSON {
    companion object {
        private val gson by lazy { Gson() }

        internal inline fun <reified T> parse(json: String): T {
            val type = object : TypeToken<T>() {}.type
            return gson.fromJson<T>(json, type)
        }

        internal fun stringify(obj: Any): String {
            return gson.toJson(obj)
        }
    }
}