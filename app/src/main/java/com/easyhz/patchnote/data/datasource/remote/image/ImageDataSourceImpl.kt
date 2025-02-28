package com.easyhz.patchnote.data.datasource.remote.image

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.easyhz.patchnote.core.common.constant.CacheDirectory.DOWNLOAD_IMAGES
import com.easyhz.patchnote.core.common.constant.CacheDirectory.DOWNLOAD_IMAGE_PREFIX
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.common.util.Generate
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storageMetadata
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class ImageDataSourceImpl @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val storage: FirebaseStorage,
) : ImageDataSource {
    override suspend fun uploadImage(pathId: String, imageUri: Uri, imageName: String): Result<String> = withContext(dispatcher) {
        runCatching {
            val imageRef = storage.reference.child("$pathId/$imageName")
            val metadata = storageMetadata {
                contentType = "image/jpeg"
            }

            imageRef.putFile(imageUri, metadata).await()
            imageRef.downloadUrl.await().toString()
        }
    }

    override suspend fun downloadImage(context: Context, imageUrl: String, id: String): Result<Uri> = withContext(dispatcher) {
        runCatching {
            val storageRef = storage.getReferenceFromUrl(imageUrl)

            val cacheDir = File(context.cacheDir, DOWNLOAD_IMAGES)
            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
            }

            val file = File(cacheDir, "$DOWNLOAD_IMAGE_PREFIX$id.jpeg")
            storageRef.getFile(file).await()

            file.toUri()
        }
    }
}