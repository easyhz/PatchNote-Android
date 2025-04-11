package com.easyhz.patchnote.domain.usecase.image

import android.app.Activity
import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.common.error.AppError
import com.easyhz.patchnote.core.common.helper.capture.CaptureHelper
import javax.inject.Inject

data class CaptureParam(
    val activity: Activity,
    val content: @Composable () -> Unit,
)

class GenerateBitmapFromComposableUseCase @Inject constructor(
    private val captureHelper: CaptureHelper,
): BaseUseCase<CaptureParam, Bitmap>() {
    override suspend fun invoke(param: CaptureParam): Result<Bitmap> {
        return runCatching {
            captureHelper.captureComposable(
                activity = param.activity,
                content = param.content
            ) ?: throw AppError.UnexpectedError
        }
    }
}