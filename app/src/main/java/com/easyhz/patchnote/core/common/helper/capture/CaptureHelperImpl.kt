package com.easyhz.patchnote.core.common.helper.capture

import android.app.Activity
import android.graphics.Bitmap
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.drawToBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CaptureHelperImpl @Inject constructor(

): CaptureHelper {
    override suspend fun captureComposable(
        activity: Activity,
        content: @Composable () -> Unit
    ): Bitmap? = withContext(Dispatchers.Main) {
        try {
            val composeView = ComposeView(activity)
            val rootView = FrameLayout(activity).apply {
                addView(composeView)
            }

            activity.addContentView(
                rootView,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )

            composeView.setContent {
                content()
            }

            suspendCoroutine { continuation ->
                composeView.viewTreeObserver.addOnPreDrawListener(object :
                    ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        composeView.viewTreeObserver.removeOnPreDrawListener(this)

                        val bitmap = composeView.drawToBitmap()
                        continuation.resume(bitmap)

                        (rootView.parent as? ViewGroup)?.removeView(rootView)
                        return true
                    }
                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}