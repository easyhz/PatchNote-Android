package com.easyhz.patchnote.core.common.util.log

import android.util.Log
import com.easyhz.patchnote.core.common.util.log.Logger
import javax.inject.Inject

internal class AppLogger @Inject constructor(

): Logger {
    override fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun e(tag: String, message: String, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }
}