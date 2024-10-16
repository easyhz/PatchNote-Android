package com.easyhz.patchnote.data.model.defect.data

import com.easyhz.patchnote.core.common.util.DateFormatUtil
import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class DefectCompletionData(
    @PropertyName("afterDescription")
    val afterDescription: String = "",
    @PropertyName("afterImageSizes")
    val afterImageSizes: List<ImageSizeData> = emptyList(),
    @get:PropertyName("afterImageURLs")
    @set:PropertyName("afterImageURLs")
    var afterImageUrls: List<String> = emptyList(),
    @get:PropertyName("workerID")
    @set:PropertyName("workerID")
    var workerId: String? = null,
    @PropertyName("workerName")
    val workerName: String? = null,
    @PropertyName("workerPhone")
    val workerPhone: String? = null,
    @PropertyName("completionDate")
    val completionDate: Timestamp? = Timestamp.now(),
    @PropertyName("completionDateStr")
    val completionDateStr: String = DateFormatUtil.formatTimestampToDateString(Timestamp.now())
)