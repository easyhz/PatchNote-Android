package com.easyhz.patchnote.ui.navigation.defect

import com.easyhz.patchnote.core.common.util.serializableType
import com.easyhz.patchnote.core.common.util.urlEncode
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.defect.DefectProgress
import com.easyhz.patchnote.core.model.image.ImageSize
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class DefectDetail(
    val defectItem: DefectItemArgs,
    val isRefresh: Boolean = false
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
    site = site.urlEncode(),
    building = building.urlEncode(),
    unit = unit.urlEncode(),
    space = space.urlEncode(),
    part = part.urlEncode(),
    workType = workType.urlEncode(),
    progress = progress,
    thumbnailUrl = thumbnailUrl.urlEncode(),
    beforeDescription = beforeDescription.urlEncode(),
    beforeImageSizes = beforeImageSizes,
    beforeImageUrls = beforeImageUrls.map { it.urlEncode() },
    afterDescription = afterDescription.urlEncode(),
    afterImageSizes = afterImageSizes,
    afterImageUrls = afterImageUrls.map { it.urlEncode() },
    requesterId = requesterId,
    requesterName = requesterName.urlEncode(),
    requesterPhone = requesterPhone.urlEncode(),
    workerId = workerId,
    workerName = workerName?.urlEncode(),
    workerPhone = workerPhone?.urlEncode(),
    requestDate = requestDate.toString(),
    completionDate = completionDate?.toString(),
    search = search.map { it.urlEncode() },
    teamId = teamId
)