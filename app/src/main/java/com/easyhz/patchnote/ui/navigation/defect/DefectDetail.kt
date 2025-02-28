package com.easyhz.patchnote.ui.navigation.defect

import com.easyhz.patchnote.core.common.util.serializableType
import com.easyhz.patchnote.core.common.util.urlDecode
import com.easyhz.patchnote.core.common.util.urlEncode
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.defect.DefectProgress
import com.easyhz.patchnote.core.model.image.ImageSize
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class DefectDetail(
    val defectItem: DefectItemArgs,
    val isRefresh: Boolean
) {
    companion object {
        val typeMap = mapOf(
            typeOf<DefectItemArgs>() to serializableType<DefectItemArgs>()
        )
    }
}

@Serializable
data class DefectItemArgs(
    val id: String,
    val site: String,
    val building: String,
    val unit: String,
    val space: String,
    val part: String,
    val workType: String,
    val progress: DefectProgress,
    val thumbnailUrl: String,
    val beforeDescription: String,
    val beforeImageSizes: List<ImageSize>,
    val beforeImageUrls: List<String>,
    val afterDescription: String,
    val afterImageSizes: List<ImageSize>,
    val afterImageUrls: List<String>,
    val requesterId: String,
    val requesterName: String,
    val requesterPhone: String,
    val workerId: String?,
    val workerName: String?,
    val workerPhone: String?,
    val requestDate: String,
    val completionDate: String?,
    val search: List<String>,
    val teamId: String
)


internal fun DefectItem.toArgs() = DefectItemArgs(
    id = id,
    site = site,
    building = building,
    unit = unit,
    space = space,
    part = part,
    workType = workType,
    progress = progress,
    thumbnailUrl = thumbnailUrl.urlEncode(),
    beforeDescription = beforeDescription,
    beforeImageSizes = beforeImageSizes,
    beforeImageUrls = beforeImageUrls.map { it.urlEncode() },
    afterDescription = afterDescription,
    afterImageSizes = afterImageSizes,
    afterImageUrls = afterImageUrls.map { it.urlEncode() },
    requesterId = requesterId,
    requesterName = requesterName,
    requesterPhone = requesterPhone,
    workerId = workerId,
    workerName = workerName,
    workerPhone = workerPhone,
    requestDate = requestDate,
    completionDate = completionDate,
    search = search,
    teamId = teamId
)

internal fun DefectItemArgs.toModel() = DefectItem(
    id = id,
    site = site,
    building = building,
    unit = unit,
    space = space,
    part = part,
    workType = workType,
    progress = progress,
    thumbnailUrl = thumbnailUrl,
    beforeDescription = beforeDescription,
    beforeImageSizes = beforeImageSizes,
    beforeImageUrls = beforeImageUrls.map { it.urlDecode() },
    afterDescription = afterDescription,
    afterImageSizes = afterImageSizes,
    afterImageUrls = afterImageUrls.map { it.urlDecode() },
    requesterId = requesterId,
    requesterName = requesterName,
    requesterPhone = requesterPhone,
    workerId = workerId,
    workerName = workerName,
    workerPhone = workerPhone,
    requestDate = requestDate,
    completionDate = completionDate,
    search = search,
    teamId = teamId
)