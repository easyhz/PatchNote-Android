package com.easyhz.patchnote.core.common.util

import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject

class CrashlyticsLoggerImpl @Inject constructor(
    private val crashlytics: FirebaseCrashlytics,
): CrashlyticsLogger {
    override fun log(message: String) {
        crashlytics.log(message)
    }

    override fun logException(throwable: Throwable) {
        crashlytics.recordException(throwable)
    }

    override fun setKey(key: String, value: String) {
        crashlytics.setCustomKey(key, value)
    }
}