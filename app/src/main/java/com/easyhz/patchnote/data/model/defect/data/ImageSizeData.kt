package com.easyhz.patchnote.data.model.defect.data

import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName

@IgnoreExtraProperties
data class ImageSizeData(
    @PropertyName("height")
    val height: Long = 0,
    @PropertyName("width")
    val width: Long = 0
)
