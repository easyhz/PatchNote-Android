package com.easyhz.patchnote.data.mapper.defect

import com.easyhz.patchnote.core.model.defect.DefectCompletion
import com.easyhz.patchnote.data.model.defect.data.DefectCompletionData
import com.google.firebase.Timestamp

fun DefectCompletion.toData() : DefectCompletionData {
    return DefectCompletionData(
        afterDescription = afterDescription,
        afterImageUrls = afterImageUrls,
        workerId = workerId,
        workerName = workerName,
        workerPhone = workerPhone,
        completionDate = Timestamp.now()
    )
}