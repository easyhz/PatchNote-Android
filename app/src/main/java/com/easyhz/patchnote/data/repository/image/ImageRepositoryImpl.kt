package com.easyhz.patchnote.data.repository.image

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.common.util.Generate
import com.easyhz.patchnote.core.model.image.DefectImage
import com.easyhz.patchnote.core.model.image.DisplayImageType
import com.easyhz.patchnote.core.model.image.ImageSize
import com.easyhz.patchnote.data.datasource.local.image.ImageLocalDataSource
import com.easyhz.patchnote.data.datasource.remote.image.ImageDataSource
import com.easyhz.patchnote.data.provider.PatchNoteFileProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(PatchNoteDispatchers.DEFAULT) private val defaultDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
    private val imageDataSource: ImageDataSource,
    private val imageLocalDataSource: ImageLocalDataSource
) : ImageRepository {

    override suspend fun getTakePictureUri(): Result<Uri> =
        PatchNoteFileProvider.getTakePictureUri(context, ioDispatcher)

    override suspend fun uploadImages(pathId: String, images: List<Uri>): Result<List<String>> =
        runCatching {
            withContext(ioDispatcher) {
                if (images.isEmpty()) return@withContext emptyList()
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
        runCatching {
            withContext(ioDispatcher) {
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
        runCatching {
            withContext(ioDispatcher) {
                imageUri.map {
                    PatchNoteFileProvider.getImageDimensions(context, it)
                }
            }
        }

    override suspend fun rotateImage(imageUri: Uri): Result<Unit> = runCatching {
        withContext(ioDispatcher) {
            PatchNoteFileProvider.rotateAndSaveImage(context, imageUri)
        }
    }

    override suspend fun saveOfflineImages(imageUri: List<Uri>): Result<List<Uri?>> =
        runCatching {
            withContext(ioDispatcher) {
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

    override suspend fun getDefectImages(imageUrls: List<String>): Result<List<DefectImage>> =
        runCatching {
            withContext(ioDispatcher) {
                imageUrls.map {
                    val id = Generate.randomUuid()
                    val uri =
                        imageDataSource.downloadImage(context = context, imageUrl = it, id = id)
                            .getOrThrow()
                    DefectImage(id = id, uri = uri)
                }
            }
        }

    override suspend fun loadBitmapFromUrl(imageUrl: String): Result<Bitmap?> {
        return imageDataSource.loadBitmapFromUrl(imageUrl)
    }

    override suspend fun saveImageToBitmap(bitmap: Bitmap): Result<Unit> {
        return imageDataSource.saveImage(bitmap)
    }

    override fun getImageSetting(): Flow<LinkedHashMap<DisplayImageType, Boolean>> {
        return imageLocalDataSource.getImageSetting()
    }

    override suspend fun setImageSetting(
        categoryType: DisplayImageType,
        newValue: Boolean
    ) = imageLocalDataSource.setImageSetting(
            categoryType = categoryType,
            newValue = newValue
        )
}
