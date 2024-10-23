package com.easyhz.patchnote.data.provider

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.constant.CacheDirectory
import com.easyhz.patchnote.core.model.image.ImageSize
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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

                saveCompressedImage(context, compressedData, "${imageUri.lastPathSegment}")
            }
        }

        /**
         *  이미지 가로 세로를 가져오는 함수
         *
         *  @return ImageSize
         */
        fun getImageDimensions(context: Context, uri: Uri): ImageSize {
            try {
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val options = BitmapFactory.Options().apply {
                        inJustDecodeBounds = true
                    }
                    BitmapFactory.decodeStream(inputStream, null, options)
                    return ImageSize(height = options.outHeight.toLong(), width = options.outWidth.toLong())
                } ?: return ImageSize(938, 938)
            } catch (e: Exception) {
                Log.e("PatchNoteFileProvider", "Failed to get image dimensions", e)
                return ImageSize(938, 938)
            }
        }

        /**
         * 이미지를 회전시키고 저장하는 함수
         */
        fun rotateAndSaveImage(context: Context, uri: Uri) {
            try {
                val bitmap = uriToBitmap(context, uri)
                val rotatedBitmap = when (val orientation = getOrientationOfImage(context, uri)) {
                    90, 180, 270 -> rotateBitmap(bitmap, orientation)
                    else -> bitmap
                }

                saveBitmapToUri(context, rotatedBitmap, uri)
            } catch (e: Exception) {
                Log.e("PatchNoteFileProvider", "Failed to rotate and save image", e)
            }
        }

        private fun saveBitmapToUri(context: Context, bitmap: Bitmap, uri: Uri) {
            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
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
            val dir = File(context.cacheDir, CacheDirectory.COMPRESSED_IMAGES)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val compressedFile = File(dir, "${CacheDirectory.COMPRESSED_IMAGE_PREFIX}${originalFileName}_${System.currentTimeMillis()}.jpeg")
            FileOutputStream(compressedFile).use { fos ->
                fos.write(compressedData)
                fos.flush()
            }
            return Uri.fromFile(compressedFile)
        }

        private fun getOrientationOfImage(context: Context, uri: Uri): Int {
            val inputStream = context.contentResolver.openInputStream(uri)
            val exif: ExifInterface? = try {
                ExifInterface(inputStream!!)
            } catch (e: IOException) {
                e.printStackTrace()
                return -1
            }
            inputStream.close()

            val orientation = exif?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            if (orientation != -1) {
                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> return 90
                    ExifInterface.ORIENTATION_ROTATE_180 -> return 180
                    ExifInterface.ORIENTATION_ROTATE_270 -> return 270
                }
            }
            return 0
        }

        private fun rotateBitmap(bitmap: Bitmap, degree: Int): Bitmap {
            val matrix = Matrix()
            matrix.postRotate(degree.toFloat())
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }
    }


}