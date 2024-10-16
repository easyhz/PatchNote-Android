package com.easyhz.patchnote.data.mapper.defect

import com.easyhz.patchnote.core.common.util.DateFormatUtil
import com.easyhz.patchnote.core.model.defect.DefectCompletion
import com.easyhz.patchnote.data.mapper.image.toData
import com.easyhz.patchnote.data.model.defect.data.DefectCompletionData
import com.google.firebase.Timestamp

fun DefectCompletion.toData() : DefectCompletionData {
    return DefectCompletionData(
        afterDescription = afterDescription,
        afterImageUrls = afterImageUrls,
        afterImageSizes = afterImageSizes.map { it.toData() },
        workerId = workerId,
        workerName = workerName,
        workerPhone = workerPhone,
        completionDate = Timestamp.now(),
        completionDateStr = DateFormatUtil.formatTimestampToDateString(Timestamp.now())
    )
}