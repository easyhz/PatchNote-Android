package com.easyhz.patchnote.core.model.defect

data class OfflineDefect(
    val id: String,
    val site: String,
    val building: String,
    val unit: String,
    val space: String,
    val part: String,
    val workType: String,
    val beforeDescription: String,
    val requesterId: String,
    val requesterName: String,
    val requesterPhone: String,
    val teamId: String,
    val thumbnailUrl: String,
    val creationTime: Long = System.currentTimeMillis(),
    val images: List<OfflineDefectImage>
)

data class OfflineDefectImage(
    val id: Int = 0,
    val defectId: String,
    val url: String,
    val height: Long,
    val width: Long,
)
