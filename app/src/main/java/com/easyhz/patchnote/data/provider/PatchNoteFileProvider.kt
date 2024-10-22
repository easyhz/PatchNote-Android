package com.easyhz.patchnote.data.provider

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.constant.CacheDirectory
import com.easyhz.patchnote.core.model.image.ImageSize
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class PatchNoteFileProvider : FileProvider(R.xml.file_path) {
    companion object {
        suspend fun getTakePictureUri(
            context: Context,
            dispatcher: CoroutineDispatcher
        ): Result<Uri> = withContext(dispatcher) {
            runCatching {
                val directory =
                    File(context.cacheDir, CacheDirectory.CAMERA_IMAGES).apply { mkdirs() }
                val file = File.createTempFile(
                    CacheDirectory.CAMERA_IMAGE_PREFIX,
                    ".jpeg",
                    directory
                )
                val authority = "${context.packageName}.file_provider"
                getUriForFile(context, authority, file)
            }
        }

        suspend fun compressImageUriToMaxSize(
            context: Context,
            dispatcher: CoroutineDispatcher,
            imageUri: Uri,
            maxFileSizeMB: Double = 0.5
        ): Result<Uri> = withContext(dispatcher) {
            runCatching {
                val maxSizeBytes = (maxFileSizeMB * 1024 * 1024).toLong()
                val originalBitmap = uriToBitmap(context, imageUri)

                val originalData = compressBitmapQuality(originalBitmap, 100)
                val originalSize = originalData.size.toLong()

                if (originalSize <= maxSizeBytes) {
                    return@runCatching imageUri
                }

                var quality = 100
                var compressedData: ByteArray
                var resizedBitmap = originalBitmap
                var resized = false

                do {
                    compressedData = compressBitmapQuality(resizedBitmap, quality)
                    val currentSize = compressedData.size.toLong()

                    if (currentSize > maxSizeBytes) {
                        quality -= 5
                        if (quality < 50 && !resized) {
                            resizedBitmap = resizeBitmap(resizedBitmap, resizedBitmap.width / 2, resizedBitmap.height / 2)
                            quality = 100
                            resized = true
                        }
                    }
                } while (compressedData.size > maxSizeBytes && quality > 0)

                saveCompressedImage(context, compressedData, "compressed_image_${imageUri.lastPathSegment}")
            }
        }

        /**
         *  이미지 가로 세로를 가져오는 함수
         *
         *  @return ImageSize
         */
        fun getImageDimensions(context: Context, uri: Uri): ImageSize {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val options = BitmapFactory.Options().apply {
                    inJustDecodeBounds = true
                }
                BitmapFactory.decodeStream(inputStream, null, options)
                return ImageSize(height = options.outHeight.toLong(), width = options.outWidth.toLong())
            } ?: return ImageSize(938, 938)
        }

        private fun compressBitmapQuality(bitmap: Bitmap, quality: Int): ByteArray {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
            return byteArrayOutputStream.toByteArray()
        }

        private fun uriToBitmap(context: Context, uri: Uri): Bitmap {
            return context.contentResolver.openInputStream(uri).use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        }

        private fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
            val width = bitmap.width
            val height = bitmap.height
            val aspectRatio = width.toFloat() / height.toFloat()

            var newWidth = maxWidth
            var newHeight = maxHeight

            if (width > height) {
                newHeight = (maxWidth / aspectRatio).toInt()
            } else if (height > width) {
                newWidth = (maxHeight * aspectRatio).toInt()
            }

            return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
        }

        private fun saveCompressedImage(context: Context, compressedData: ByteArray, originalFileName: String): Uri {
            val compressedFile = File(context.cacheDir, "compressed_${originalFileName}_${System.currentTimeMillis()}.jpeg")
            FileOutputStream(compressedFile).use { fos ->
                fos.write(compressedData)
                fos.flush()
            }
            return Uri.fromFile(compressedFile)
        }
    }
}