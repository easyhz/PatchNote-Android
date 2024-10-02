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

        suspend fun compressImageUri(
            context: Context,
            dispatcher: CoroutineDispatcher,
            imageUri: Uri,
            target: Int
        ): Result<Uri> = withContext(dispatcher) {
            runCatching {
                val originalFile = uriToFile(context, imageUri)
                val originalSize = originalFile.length()
                val targetSize = originalSize * target / 100

                var quality = 100
                var compressedFile: File

                do {
                    val bitmap = uriToBitmap(context, imageUri)

                    val compressedBitmapData = compressBitmapQuality(bitmap, quality)   // 압축

                    compressedFile = File(context.cacheDir, "compressed_${originalFile.name}")
                    val fileOutputStream = FileOutputStream(compressedFile)
                    fileOutputStream.write(compressedBitmapData)
                    fileOutputStream.flush()
                    fileOutputStream.close()

                    val compressedFileSize = compressedFile.length()

                    quality -= 5

                } while (compressedFileSize > targetSize && quality > 0)
                Uri.fromFile(compressedFile)
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

        private fun uriToFile(context: Context, uri: Uri): File {
            val inputStream = context.contentResolver.openInputStream(uri)
            val tempFile = File(context.cacheDir, "temp_image_file")
            tempFile.outputStream().use { outputStream ->
                inputStream?.copyTo(outputStream)
            }
            return tempFile
        }
    }
}