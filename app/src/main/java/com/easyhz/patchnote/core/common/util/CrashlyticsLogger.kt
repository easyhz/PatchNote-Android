package com.easyhz.patchnote.core.common.util

interface CrashlyticsLogger {
    fun log(message: String)
    fun logException(throwable: Throwable)
    fun setKey(key: String, value: String)
}