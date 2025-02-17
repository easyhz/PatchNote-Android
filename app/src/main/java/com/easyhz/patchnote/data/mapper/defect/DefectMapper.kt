package com.easyhz.patchnote.data.mapper.defect

import com.easyhz.patchnote.core.common.util.DateFormatUtil
import com.easyhz.patchnote.core.database.defect.entity.OfflineDefectEntity
import com.easyhz.patchnote.core.database.defect.entity.OfflineDefectImageEntity
import com.easyhz.patchnote.core.database.defect.model.OfflineDefect
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.defect.DefectProgress
import com.easyhz.patchnote.core.model.defect.EntryDefect
import com.easyhz.patchnote.core.model.defect.ExportDefect
import com.easyhz.patchnote.core.model.image.ImageSize
import com.easyhz.patchnote.data.mapper.image.toData
import com.easyhz.patchnote.data.mapper.image.toModel
import com.easyhz.patchnote.data.model.defect.data.DefectData
import com.google.firebase.Timestamp

fun EntryDefect.toData(): DefectData = DefectData(
    id = id,
    site = site,
    building = building,
    unit = unit,
    space = space,
    part = part,
    workType = workType,
    thumbnailUrl = thumbnailUrl,
    beforeDescription = beforeDescription,
    beforeImageSizes = beforeImageSizes.map { it.toData() },
    beforeImageUrls = beforeImageUrls,
    requesterId = requesterId,
    requestDate = Timestamp.now(),
    requesterName = requesterName,
    requesterPhone = requesterPhone,
    search = this.exportSearch(),
    teamId = teamId,
    isDeleted = false,
)

fun EntryDefect.toEntity(): OfflineDefect {
    val offlineDefectEntity = OfflineDefectEntity(
        id = id,
        site = site,
        building = building,
        unit = unit,
        space = space,
        part = part,
        workType = workType,
        beforeDescription = beforeDescription,
        requesterId = requesterId,
        requesterName = requesterName,
        requesterPhone = requesterPhone,
        teamId = teamId,
        thumbnailUrl = thumbnailUrl,
    )

    val offlineDefectImageEntities = beforeImageUrls.zip(beforeImageSizes).map { (url, size) ->
        OfflineDefectImageEntity(
            defectId = id,
            url = url,
            width = size.width,
            height = size.height,
        )
    }

    return OfflineDefect(
        defect = offlineDefectEntity,
        images = offlineDefectImageEntities,
    )
}

fun DefectData.toModel(): DefectItem = DefectItem(
    id = id,
    site = site,
    building = building,
    unit = unit,
    space = space,
    part = part,
    workType = workType,
    progress = DefectProgress.valueOf(progress),
    thumbnailUrl = thumbnailUrl,
    beforeDescription = beforeDescription,
    beforeImageSizes = beforeImageSizes.map { it.toModel() },
    beforeImageUrls = beforeImageUrls,
    requesterId = requesterId,
    requesterName = requesterName,
    requesterPhone = requesterPhone,
    workerId = workerId,
    workerName = workerName,
    workerPhone = workerPhone,
    afterDescription = afterDescription,
    afterImageSizes = afterImageSizes.map { it.toModel() },
    afterImageUrls = afterImageUrls,
    requestDate = DateFormatUtil.formatTimestampToDateString(requestDate),
    completionDate = DateFormatUtil.formatTimestampToDateNullString(completionDate),
    search = search,
    teamId = teamId,
)

fun DefectItem.toExportDefect() = ExportDefect(
    id = id,
    site = site,
    building = building,
    unit = unit,
    space = space,
    part = part,
    workType = workType,
    progress = progress,
    requesterName = requesterName,
    requesterPhone = requesterPhone.takeLast(4),
    requestDate = requestDate,
    beforeDescription = beforeDescription.replace("\n", " "),
    workerName = workerName ?: "",
    workerPhone = workerPhone?.takeLast(4) ?: "",
    completionDate = completionDate ?: "",
    afterDescription = afterDescription.replace("\n", " "),
    thumbnail = thumbnailUrl
)

fun OfflineDefect.toModel() = DefectItem(
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
    beforeImageSizes = images.map { it.toModel() },
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

private fun OfflineDefectImageEntity.toModel() = ImageSize(
    height = height, width = width
)