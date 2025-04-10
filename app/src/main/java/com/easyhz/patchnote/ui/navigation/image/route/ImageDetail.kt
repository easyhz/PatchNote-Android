package com.easyhz.patchnote.ui.navigation.image.route

import com.easyhz.patchnote.core.common.util.serializableType
import com.easyhz.patchnote.core.common.util.urlEncode
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class ImageDetail(
    val imageDetailArgs: ImageDetailArgs,
) {
    companion object {
        val typeMap = mapOf(
            typeOf<ImageDetailArgs>() to serializableType<ImageDetailArgs>()
        )
    }
}

@Serializable
data class ImageDetailArgs(
    val images: List<String>,
    val currentImage: Int,
) {
    companion object {
        fun create(images: List<String>, currentImage: Int): ImageDetailArgs {
            return ImageDetailArgs(
                images = images.map { it.urlEncode() },
                currentImage = currentImage
            )
        }
    }
}