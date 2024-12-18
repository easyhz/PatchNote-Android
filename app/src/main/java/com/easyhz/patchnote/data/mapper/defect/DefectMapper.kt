package com.easyhz.patchnote.data.mapper.defect

import android.content.Context
import com.easyhz.patchnote.core.common.util.DateFormatUtil
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.defect.DefectProgress
import com.easyhz.patchnote.core.model.defect.EntryDefect
import com.easyhz.patchnote.core.model.defect.ExportDefect
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
    beforeDescription = beforeDescription,
    workerName = workerName ?: "",
    workerPhone = workerPhone?.takeLast(4) ?: "",
    completionDate = completionDate ?: "",
    afterDescription = afterDescription,
    thumbnail = thumbnailUrl
)