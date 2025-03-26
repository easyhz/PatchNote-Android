package com.easyhz.patchnote.domain.usecase.defect.offline

import androidx.core.net.toUri
import com.easyhz.patchnote.core.common.constant.Storage
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.model.defect.EntryDefect
import com.easyhz.patchnote.core.model.defect.OfflineDefect
import com.easyhz.patchnote.core.model.defect.OfflineDefectProgress
import com.easyhz.patchnote.core.model.image.ImageSize
import com.easyhz.patchnote.data.repository.defect.DefectRepository
import com.easyhz.patchnote.data.repository.image.ImageRepository
import com.easyhz.patchnote.data.repository.user.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class UploadAllOfflineDefectToRemoteUseCase @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val defectRepository: DefectRepository,
    private val imageRepository: ImageRepository,
    private val userRepository: UserRepository,
) {
    operator fun invoke(
        concurrency: Int = 5
    ): Flow<OfflineDefectProgress> = channelFlow {
        val user = userRepository.getUserFromLocal().getOrThrow()

        val defects = defectRepository.findOfflineDefects(user.currentTeamId!!, user.id)
        val total = defects.size

        send(OfflineDefectProgress(total = total, uploaded = 0))

        val semaphore = Semaphore(concurrency)
        val uploadedCounter = AtomicInteger(0)

        defects.forEach { defect ->
            launch {
                semaphore.acquire()
                try {
                    val entryDefect = getEntryDefectModel(defect)

                    defectRepository.createDefect(entryDefect)
                    defectRepository.deleteOfflineDefect(entryDefect.id)
                } finally {
                    semaphore.release()
                }

                val currentCount = uploadedCounter.incrementAndGet()

                send(OfflineDefectProgress(total = total, uploaded = currentCount))
            }
        }
    }.flowOn(dispatcher)


    private suspend fun getEntryDefectModel(defect: OfflineDefect): EntryDefect = withContext(dispatcher) {
        val imageUrlsDeferred = async { imageRepository.uploadImages("${Storage.DEFECT}/${defect.id}/Before/", defect.images.map { it.url.toUri() }) }
        val thumbnailUrlDeferred = async {
            val thumbnail = defect.images.firstOrNull() ?: return@async Result.failure(Exception("Thumbnail is not found"))
            imageRepository.uploadThumbnail("${Storage.DEFECT}/${defect.id}/", thumbnail.url.toUri())
        }
        val imageUrls = imageUrlsDeferred.await().getOrThrow()
        val thumbnailUrl = thumbnailUrlDeferred.await().getOrThrow()
        val imageSizes = defect.images.map { ImageSize(height = it.height, width =  it.width) }

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
