package com.easyhz.patchnote.core.model.defect

import android.net.Uri
import com.easyhz.patchnote.core.model.image.ImageSize

data class DefectCompletion(
    val id: String,
    val afterDescription: String,
    val afterImageSizes: List<ImageSize>,
    val afterImageUrls: List<String>,
    val workerId: String,
    val workerName: String,
    val workerPhone: String,
)

data class DefectCompletionParam(
    val afterDescription: String,
    val afterImageUris: List<Uri>
) {

    fun toDefectCompletion(
        id: String,
        workerId: String,
        workerName: String,
        workerPhone: String,
        afterImageUrls: List<String>,
        afterImageSizes: List<ImageSize>
    ): DefectCompletion {
        return DefectCompletion(
            id = id,
            afterDescription = afterDescription,
            afterImageSizes = afterImageSizes,
            afterImageUrls = afterImageUrls,
            workerId = workerId,
            workerName = workerName,
            workerPhone = workerPhone
        )
    }
}
