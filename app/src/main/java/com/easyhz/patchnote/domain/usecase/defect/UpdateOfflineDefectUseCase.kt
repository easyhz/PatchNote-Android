package com.easyhz.patchnote.domain.usecase.defect

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.model.defect.EntryDefectParam
import com.easyhz.patchnote.core.model.defect.EntryDefectParam.Companion.toEntryDefect
import com.easyhz.patchnote.data.repository.defect.DefectRepository
import com.easyhz.patchnote.data.repository.image.ImageRepository
import com.easyhz.patchnote.data.repository.user.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateOfflineDefectUseCase @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val defectRepository: DefectRepository,
    private val imageRepository: ImageRepository,
    private val userRepository: UserRepository,
) : BaseUseCase<EntryDefectParam, Unit>() {
    override suspend fun invoke(param: EntryDefectParam): Result<Unit> = withContext(dispatcher) {
        runCatching {
            val userDeferred = async { userRepository.getUserFromLocal() }
            val offlineImageUrisDeferred = async { imageRepository.saveOfflineImages(param.beforeImageUris) }
            val imageSizesDeferred = async { imageRepository.getImageSizes(param.beforeImageUris) }
            val offlineImageUris = offlineImageUrisDeferred.await().getOrElse { emptyList() }
            val imageSizes = imageSizesDeferred.await().getOrElse { emptyList() }
            val user = userDeferred.await().getOrThrow()

            val entryDefect = param.toEntryDefect(
                teamId = user.teamId,
                requesterId = user.id,
                requesterName = user.name,
                requesterPhone = user.phone,
                thumbnailUrl = offlineImageUris.firstOrNull().toString(),
                beforeImageUrls = offlineImageUris.map { it.toString() },
                beforeImageSizes = imageSizes
            )

            defectRepository.updateOfflineDefect(entryDefect).getOrThrow()
        }
    }
}