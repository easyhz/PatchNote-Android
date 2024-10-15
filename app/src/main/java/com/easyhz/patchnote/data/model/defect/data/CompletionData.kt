package com.easyhz.patchnote.data.model.defect.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class CompletionData(
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
    @PropertyName("requestDate")
    val requestDate: Timestamp = Timestamp.now(),
    @PropertyName("completionDate")
    val completionDate: Timestamp? = Timestamp.now()
)