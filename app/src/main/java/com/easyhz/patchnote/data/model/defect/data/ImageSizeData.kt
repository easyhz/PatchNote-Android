package com.easyhz.patchnote.data.model.defect.data

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageSizeData(
    @PropertyName("height")
    val height: Long = 0,
    @PropertyName("width")
    val width: Long = 0
): Parcelable
