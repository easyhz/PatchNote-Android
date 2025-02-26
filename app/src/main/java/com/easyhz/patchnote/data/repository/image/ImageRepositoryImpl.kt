package com.easyhz.patchnote.data.repository.image

import android.content.Context
import android.net.Uri
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.model.image.ImageSize
import com.easyhz.patchnote.data.datasource.remote.image.ImageDataSource
import com.easyhz.patchnote.data.provider.PatchNoteFileProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(PatchNoteDispatchers.DEFAULT) private val defaultDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
    private val imageDataSource: ImageDataSource,
) : ImageRepository {

    override suspend fun getTakePictureUri(): Result<Uri> =
        PatchNoteFileProvider.getTakePictureUri(context, ioDispatcher)

    override suspend fun uploadImages(pathId: String, images: List<Uri>): Result<List<String>> =
        withContext(ioDispatcher) {
            runCatching {
                if (images.isEmpty()) return@runCatching emptyList()
                val semaphore = Semaphore(5)

                coroutineScope {
                    images.mapIndexed { index, image ->
                        async {
                            semaphore.withPermit {
                                val imageUri = PatchNoteFileProvider.compress(
                                    context = context,
                                    imageUri = image,
                                    defaultDispatcher = defaultDispatcher,
                                    ioDispatcher = ioDispatcher
                                )

                                imageDataSource.uploadImage(
                                    pathId = pathId,
                                    imageUri = imageUri,
                                    imageName = "$index"
                                ).getOrThrow()
                            }
                        }
                    }.awaitAll()
                }
            }
        }

    override suspend fun uploadThumbnail(pathId: String, imageUri: Uri): Result<String> =
        withContext(ioDispatcher) {
            runCatching {
                val thumbnail = PatchNoteFileProvider.compress(
                    context = context,
                    imageUri = imageUri,
                    defaultDispatcher = defaultDispatcher,
                    ioDispatcher = ioDispatcher
                )

                imageDataSource.uploadImage(
                    pathId = pathId,
                    imageUri = thumbnail,
                    imageName = "thumbnail"
                ).getOrThrow()
            }
        }

    override suspend fun getImageSizes(imageUri: List<Uri>): Result<List<ImageSize>> =
        withContext(ioDispatcher) {
            runCatching {
                imageUri.map {
                    PatchNoteFileProvider.getImageDimensions(context, it)
                }
            }
        }

    override suspend fun rotateImage(imageUri: Uri): Result<Unit> = withContext(ioDispatcher) {
        runCatching {
            PatchNoteFileProvider.rotateAndSaveImage(context, imageUri)
        }
    }

    override suspend fun saveOfflineImages(imageUri: List<Uri>): Result<List<Uri?>> =
        withContext(ioDispatcher) {
            return@withContext runCatching {
                imageUri.map {
                    PatchNoteFileProvider.compress(
                        context = context,
                        imageUri = it,
                        defaultDispatcher = defaultDispatcher,
                        ioDispatcher = ioDispatcher
                    )
                }
            }
        }
}
