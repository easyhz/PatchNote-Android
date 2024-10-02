package com.easyhz.patchnote.data.model.defect.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageSize(
    val height: Long,
    val width: Long
): Parcelable