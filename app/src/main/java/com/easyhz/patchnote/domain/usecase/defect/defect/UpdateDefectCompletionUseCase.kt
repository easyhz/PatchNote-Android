package com.easyhz.patchnote.domain.usecase.defect

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.common.constant.Storage
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.model.defect.DefectCompletionParam
import com.easyhz.patchnote.data.repository.defect.DefectRepository
import com.easyhz.patchnote.data.repository.image.ImageRepository
import com.easyhz.patchnote.data.repository.user.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateDefectCompletionUseCase @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val defectRepository: DefectRepository,
    private val imageRepository: ImageRepository,
    private val userRepository: UserRepository,
): BaseUseCase<DefectCompletionParam, Unit>() {
    override suspend fun invoke(param: DefectCompletionParam): Result<Unit> = runCatching {
        withContext(dispatcher) {
            val userDeferred = async { userRepository.getUserFromLocal() }
            val imageUrlsDeferred = async { imageRepository.uploadImages("${Storage.DEFECT}/${param.id}/After/", param.afterImageUris) }
            val imageSizesDeferred = async { imageRepository.getImageSizes(param.afterImageUris) }

            val imageUrls = imageUrlsDeferred.await().getOrThrow()
            val imageSizes = imageSizesDeferred.await().getOrThrow()
            val user = userDeferred.await().getOrThrow()

            val defectCompletion = param.toDefectCompletion(
                workerId = user.id,
                workerName = user.name,
                workerPhone = user.phone,
                afterImageUrls = imageUrls,
                afterImageSizes = imageSizes
            )
            defectRepository.updateDefectCompletion(defectCompletion).getOrThrow()
        }
    }
}