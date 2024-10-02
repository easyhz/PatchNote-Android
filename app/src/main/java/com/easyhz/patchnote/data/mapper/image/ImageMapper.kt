package com.easyhz.patchnote.data.mapper.image

import com.easyhz.patchnote.core.model.image.ImageSize
import com.easyhz.patchnote.data.model.defect.data.ImageSizeData

fun ImageSizeData.toModel(): ImageSize = ImageSize(
    height = height,
    width = width
)

fun ImageSize.toData(): ImageSizeData = ImageSizeData(
    height = height,
    width = width
)