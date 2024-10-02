package com.easyhz.patchnote.core.model.defect

import android.net.Uri
import com.easyhz.patchnote.core.model.image.ImageSize

data class EntryDefect(
    val id: String,
    val site: String,
    val building: String,
    val unit: String,
    val space: String,
    val part: String,
    val workType: String,
    val thumbnailUrl: String,
    val beforeDescription: String,
    val beforeImageUrls: List<String>,
    val beforeImageSizes: List<ImageSize>,
    val requesterId: String,
    val requesterName: String,
    val requesterPhone: String,
)

data class EntryDefectParam(
    val id: String,
    val site: String,
    val building: String,
    val unit: String,
    val space: String,
    val part: String,
    val workType: String,
    val beforeDescription: String,
    val beforeImageUris: List<Uri>,
) {
    companion object {
        fun EntryDefectParam.toEntryDefect(
            requesterId: String,
            requesterName: String,
            requesterPhone: String,
            thumbnailUrl: String,
            beforeImageUrls: List<String>,
            beforeImageSizes: List<ImageSize>,
        ): EntryDefect {
            return EntryDefect(
                id = id,
                site = site,
                building = building,
                unit = unit,
                space = space,
                part = part,
                workType = workType,
                thumbnailUrl = thumbnailUrl,
                beforeDescription = beforeDescription,
                beforeImageUrls = beforeImageUrls,
                beforeImageSizes = beforeImageSizes,
                requesterId = requesterId,
                requesterName = requesterName,
                requesterPhone = requesterPhone,
            )
        }
    }
}