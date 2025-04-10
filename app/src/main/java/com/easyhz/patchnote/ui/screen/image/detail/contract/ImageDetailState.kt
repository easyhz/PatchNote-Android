package com.easyhz.patchnote.ui.screen.image.detail.contract

import com.easyhz.patchnote.core.common.base.UiState

/**
 * Date: 2025. 4. 9.
 * Time: 오후 10:05
 */

data class ImageDetailState(
    val isLoading: Boolean,
    val isDisplayInformation: Boolean,
    val images: List<String>,
    val currentImage: Int,
) : UiState() {
    companion object {
        fun init(): ImageDetailState = ImageDetailState(
            isLoading = true,
            isDisplayInformation = false,
            images = emptyList(),
            currentImage = 0
        )
    }
}
            