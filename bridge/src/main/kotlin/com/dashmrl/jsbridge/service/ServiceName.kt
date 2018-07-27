package com.dashmrl.jsbridge.service


@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD)
annotation class ServiceName(
        val name: String
)