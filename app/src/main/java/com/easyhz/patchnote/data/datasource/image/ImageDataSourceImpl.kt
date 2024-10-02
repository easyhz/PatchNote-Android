package com.easyhz.patchnote.data.datasource.image

import android.net.Uri
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageDataSourceImpl @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val storage: FirebaseStorage,
) : ImageDataSource {
    override suspend fun uploadImage(pathId: String, imageUri: Uri, imageName: String): Result<String> = withContext(dispatcher) {
        runCatching {
            val imageRef = storage.reference.child(pathId).child(imageName)
            imageRef.putFile(imageUri).await()
            imageRef.downloadUrl.await().toString()
        }
    }
}