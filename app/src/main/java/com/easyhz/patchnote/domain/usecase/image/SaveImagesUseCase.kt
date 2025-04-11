package com.easyhz.patchnote.domain.usecase.image

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.data.repository.image.ImageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveImagesUseCase @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val imageRepository: ImageRepository,
): BaseUseCase<List<String>, Unit>() {
    override suspend fun invoke(param: List<String>): Result<Unit> = withContext(ioDispatcher) {
        runCatching {
            param.map { imageUrl ->
                async {
                    imageRepository.loadBitmapFromUrl(imageUrl).getOrNull()
                        ?.let { imageRepository.saveImageToBitmap(it).getOrNull() }
                }
            }.awaitAll()
            Unit
        }
    }
}