package com.easyhz.patchnote.core.model.defect

import com.easyhz.patchnote.core.model.image.ImageSize

data class DefectContent(
    val progress: DefectProgress,
    val description: String,
    val imageSizes: List<ImageSize>,
    val imageUrls: List<String>,
)