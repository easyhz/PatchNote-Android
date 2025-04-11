package com.easyhz.patchnote.core.common.helper.capture

import android.app.Activity
import android.graphics.Bitmap
import androidx.compose.runtime.Composable

interface CaptureHelper {
    suspend fun captureComposable(activity: Activity, content: @Composable () -> Unit): Bitmap?
}