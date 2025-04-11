package com.easyhz.patchnote.ui.navigation.image.route

import com.easyhz.patchnote.core.common.util.DateFormatUtil.convertStringToIsoDateTime
import com.easyhz.patchnote.core.common.util.serializableType
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.defect.DefectProgress
import com.easyhz.patchnote.ui.navigation.defect.DefectItemArgs
import com.easyhz.patchnote.ui.navigation.defect.toArgs
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class ImageDetail(
    val imageDetailArgs: ImageDetailArgs,
) {
    companion object {
        val typeMap = mapOf(
            typeOf<ImageDetailArgs>() to serializableType<ImageDetailArgs>()
        )
    }
}

@Serializable
data class ImageDetailArgs(
    val defectItem: DefectItemArgs,
    val selectedTab: String,
    val currentImage: Int,
) {
    companion object {
        fun create(defectItem: DefectItem, selectedTab: DefectProgress, currentImage: Int): ImageDetailArgs {
            return ImageDetailArgs(
                defectItem = defectItem.toArgs(),
                selectedTab = selectedTab.name,
                currentImage = currentImage
            )
        }
    }
}

fun DefectItemArgs.toDefectItem() = DefectItem(
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
    beforeImageUrls = beforeImageUrls,
    afterDescription = afterDescription,
    afterImageSizes = afterImageSizes,
    afterImageUrls = afterImageUrls,
    requesterId = requesterId,
    requesterName = requesterName,
    requesterPhone = requesterPhone,
    workerId = workerId,
    workerName = workerName,
    workerPhone = workerPhone,
    requestDate = convertStringToIsoDateTime(requestDate),
    completionDate = completionDate?.let { convertStringToIsoDateTime(it) },
    search = search,
    teamId = teamId
)