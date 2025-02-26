package com.easyhz.patchnote.data.provider

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.exifinterface.media.ExifInterface
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.constant.CacheDirectory
import com.easyhz.patchnote.core.common.util.Generate
import com.easyhz.patchnote.core.model.image.ImageSize
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import id.zelory.compressor.constraint.size
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

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

        suspend fun compress(
            context: Context,
            imageUri: Uri,
            defaultDispatcher: CoroutineDispatcher,
            ioDispatcher: CoroutineDispatcher,
            maxFileSize: Long? = null
        ): Uri {
            try {
                val dir = File(context.cacheDir, CacheDirectory.COMPRESSED_IMAGES)
                if (!dir.exists()) {
                    dir.mkdirs()
                }

                val compressedFile = File(dir, "${CacheDirectory.COMPRESSED_IMAGE_PREFIX}${Generate.randomUUID()}_${System.currentTimeMillis()}.jpeg")
                withContext(ioDispatcher) {
                    context.contentResolver.openInputStream(imageUri)?.use { input ->
                        FileOutputStream(compressedFile).use { output ->
                            input.copyTo(output)
                        }
                    }
                }


                return Compressor.compress(context, compressedFile, defaultDispatcher) {
                    if (maxFileSize == null) {
                        default()
                    } else {
                        default()
                        size(maxFileSize)
                    }
                }.toUri()
            } catch (e: Exception) {
                Log.e("PatchNoteFileProvider", "Failed to compress image", e)
                throw e
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
                    return ImageSize(
                        height = options.outHeight.toLong(),
                        width = options.outWidth.toLong()
                    )
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

        private fun uriToBitmap(context: Context, uri: Uri): Bitmap {
            return context.contentResolver.openInputStream(uri).use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
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

            val orientation = exif?.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
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