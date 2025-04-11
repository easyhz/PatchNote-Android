package com.easyhz.patchnote.domain.usecase.image

import android.graphics.Bitmap
import android.graphics.Canvas
import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.data.repository.image.ImageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class DisplayParam(
    val images: List<String>,
    val displayInformation: Bitmap,
    val scale: Float = 0.4f
)

class SaveImagesToDisplayInformationUseCase @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val imageRepository: ImageRepository,
) : BaseUseCase<DisplayParam, Unit>() {
    override suspend fun invoke(param: DisplayParam): Result<Unit> = withContext(ioDispatcher) {
        runCatching {
            param.images.map { imageUrl ->
                async {
                    val bitmap =
                        imageRepository.loadBitmapFromUrl(imageUrl).getOrNull() ?: return@async null
                    val overlayImage = scaleBitmap(
                        base = bitmap,
                        overlay = param.displayInformation,
                        scaleRatio = param.scale
                    )
                    val padding = 4f
                    val image = mergeBitmaps(
                        base = bitmap,
                        overlay = overlayImage,
                        offsetX = padding,
                        offsetY = bitmap.height - overlayImage.height - padding
                    )
                    imageRepository.saveImageToBitmap(image).getOrNull()
                }
            }.awaitAll()
            Unit
        }
    }

    private fun scaleBitmap(base: Bitmap, overlay: Bitmap, scaleRatio: Float): Bitmap {
        val scaledWidth = (base.width * scaleRatio).toInt()
        val scaleFactor = scaledWidth.toFloat() / overlay.width
        val scaledHeight = (overlay.height * scaleFactor).toInt()

        return Bitmap.createScaledBitmap(overlay, scaledWidth, scaledHeight, true)
    }

    private fun mergeBitmaps(
        base: Bitmap,
        overlay: Bitmap,
        offsetX: Float,
        offsetY: Float
    ): Bitmap {
        val result = base.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(result)

        canvas.drawBitmap(overlay, offsetX, offsetY, null)

        return result
    }
}