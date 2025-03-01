package com.easyhz.patchnote.data.mapper.defect

import com.easyhz.patchnote.core.common.util.DateFormatUtil
import com.easyhz.patchnote.core.common.util.toDateString
import com.easyhz.patchnote.core.database.defect.entity.OfflineDefectEntity
import com.easyhz.patchnote.core.database.defect.entity.OfflineDefectImageEntity
import com.easyhz.patchnote.core.database.defect.model.OfflineDefect
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.defect.DefectProgress
import com.easyhz.patchnote.core.model.defect.EntryDefect
import com.easyhz.patchnote.core.model.defect.ExportDefect
import com.easyhz.patchnote.data.mapper.image.toData
import com.easyhz.patchnote.data.mapper.image.toModel
import com.easyhz.patchnote.data.model.defect.data.DefectData
import java.time.LocalDateTime

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
    requestDate = DateFormatUtil.localDateTimeToTimestamp(creationTime),
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

fun DefectData.toDefectItem(): DefectItem = DefectItem(
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
    requestDate = DateFormatUtil.formatTimestampToDateTime(requestDate) ?: LocalDateTime.now(),
    completionDate = DateFormatUtil.formatTimestampToDateTime(completionDate),
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
    requestDate = requestDate.toDateString(),
    beforeDescription = beforeDescription.replace("\n", " "),
    workerName = workerName ?: "",
    workerPhone = workerPhone?.takeLast(4) ?: "",
    completionDate = completionDate?.toDateString() ?: "",
    afterDescription = afterDescription.replace("\n", " "),
    thumbnail = thumbnailUrl
)