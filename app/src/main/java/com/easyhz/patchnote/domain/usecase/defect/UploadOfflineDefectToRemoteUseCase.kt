package com.easyhz.patchnote.domain.usecase.defect

import androidx.core.net.toUri
import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.common.constant.Storage
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.defect.EntryDefect
import com.easyhz.patchnote.core.model.image.ImageSize
import com.easyhz.patchnote.data.repository.defect.DefectRepository
import com.easyhz.patchnote.data.repository.image.ImageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UploadOfflineDefectToRemoteUseCase @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val defectRepository: DefectRepository,
    private val imageRepository: ImageRepository,
): BaseUseCase<DefectItem, Unit>() {

    override suspend fun invoke(param: DefectItem): Result<Unit> = runCatching {
        val entryDefect = getEntryDefectModel(param)
        defectRepository.createDefect(entryDefect)
        defectRepository.deleteOfflineDefect(entryDefect.id)
    }

    private suspend fun getEntryDefectModel(defect: DefectItem): EntryDefect = withContext(dispatcher) {
        val imageUrlsDeferred = async { imageRepository.uploadImages("${Storage.DEFECT}/${defect.id}/Before/", defect.beforeImageUrls.map { it.toUri() }) }
        val thumbnailUrlDeferred = async {
            val thumbnail = defect.beforeImageUrls.firstOrNull() ?: return@async Result.success("")
            imageRepository.uploadThumbnail("${Storage.DEFECT}/${defect.id}/", thumbnail.toUri())
        }
        val imageUrls = imageUrlsDeferred.await().getOrElse { emptyList() }
        val thumbnailUrl = thumbnailUrlDeferred.await().getOrElse { "" }
        val imageSizes = defect.beforeImageSizes.map { ImageSize(height = it.height, width =  it.width) }
        return@withContext EntryDefect(
            id = defect.id,
            site = defect.site,
            building = defect.building,
            unit = defect.unit,
            space = defect.space,
            part = defect.part,
            workType = defect.workType,
            thumbnailUrl = thumbnailUrl,
            beforeDescription = defect.beforeDescription,
            beforeImageUrls = imageUrls,
            beforeImageSizes = imageSizes,
            requesterId = defect.requesterId,
            requesterName = defect.requesterName,
            requesterPhone = defect.requesterPhone,
            teamId = defect.teamId,
        )
    }
}