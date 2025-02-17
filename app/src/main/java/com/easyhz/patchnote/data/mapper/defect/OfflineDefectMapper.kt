package com.easyhz.patchnote.data.mapper.defect

import com.easyhz.patchnote.core.common.util.DateFormatUtil
import com.easyhz.patchnote.core.database.defect.entity.OfflineDefectImageEntity
import com.easyhz.patchnote.core.database.defect.model.OfflineDefect
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.defect.DefectProgress
import com.easyhz.patchnote.core.model.image.ImageSize

fun OfflineDefect.toModel() = com.easyhz.patchnote.core.model.defect.OfflineDefect(
    id = defect.id,
    site = defect.site,
    building = defect.building,
    unit = defect.unit,
    space = defect.space,
    part = defect.part,
    workType = defect.workType,
    thumbnailUrl = defect.thumbnailUrl,
    beforeDescription = defect.beforeDescription,
    requesterId = defect.requesterId,
    requesterName = defect.requesterName,
    requesterPhone = defect.requesterPhone,
    teamId = defect.teamId,
    creationTime = defect.creationTime,
    images = images.map { it.toModel() }
)

private fun OfflineDefectImageEntity.toModel() = com.easyhz.patchnote.core.model.defect.OfflineDefectImage(
    id = id,
    defectId = defectId,
    url = url,
    height = height,
    width = width
)

fun OfflineDefect.toDefectItem() = DefectItem(
    id = defect.id,
    site = defect.site,
    building = defect.building,
    unit = defect.unit,
    space = defect.space,
    part = defect.part,
    workType = defect.workType,
    progress = DefectProgress.REQUESTED,
    thumbnailUrl = defect.thumbnailUrl,
    beforeDescription = defect.beforeDescription,
    beforeImageSizes = images.map { it.toDefectItem() },
    beforeImageUrls = images.map { it.url },
    requesterId = defect.requesterId,
    requesterName = defect.requesterName,
    requesterPhone = defect.requesterPhone,
    workerId = null,
    workerName = null,
    workerPhone = null,
    afterDescription = "",
    afterImageSizes = emptyList(),
    afterImageUrls = emptyList(),
    requestDate = DateFormatUtil.convertMillisToDate(defect.creationTime),
    completionDate = null,
    search = emptyList(),
    teamId = defect.teamId,
)

private fun OfflineDefectImageEntity.toDefectItem() = ImageSize(
    height = height, width = width
)
