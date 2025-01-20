package com.easyhz.patchnote.core.model.image

import kotlinx.serialization.Serializable

@Serializable
data class ImageSize(
    val height: Long,
    val width: Long
)