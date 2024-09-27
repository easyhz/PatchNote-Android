package com.easyhz.patchnote.core.model.image

import android.net.Uri
import com.easyhz.patchnote.core.common.util.Generate

data class DefectImage(
    val id: String,
    val uri: Uri,
) {
    companion object {
        fun create(uri: Uri): DefectImage {
            return DefectImage(
                id = Generate.randomUUID(),
                uri = uri,
            )
        }
    }
}

fun List<Uri>.toDefectImages(): List<DefectImage> {
    return map { DefectImage.create(it) }
}
