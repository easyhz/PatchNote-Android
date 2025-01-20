package com.easyhz.patchnote.core.common.util.log

interface Logger {
    fun d(tag: String, message: String)
    fun e(tag: String, message: String, throwable: Throwable? = null)
}