package com.easyhz.patchnote.data.mapper.defect

import com.easyhz.patchnote.core.model.defect.EntryDefect
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
    beforeImageSizes = beforeImageSizes,
    beforeImageUrls = beforeImageUrls,
    requesterId = requesterId,
    requesterDate = Timestamp.now(),
    requesterName = requesterName,
    requesterPhone = requesterPhone,
)