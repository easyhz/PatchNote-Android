package com.easyhz.patchnote.data.provider

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.easyhz.patchnote.R
import com.easyhz.patchnote.data.constant.CacheDirectory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File

class PatchNoteFileProvider: FileProvider(R.xml.file_path) {
    companion object {
        suspend fun getTakePictureUri(context: Context, dispatcher: CoroutineDispatcher): Result<Uri> = withContext(dispatcher) {
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
    }
}