package com.easyhz.patchnote.ui.screen.image.detail.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect

/**
 * Date: 2025. 4. 9.
 * Time: 오후 10:05
 */

sealed class ImageDetailSideEffect : UiSideEffect() {
    data object NavigateUp : ImageDetailSideEffect()
    data class ShowSnackBar(val value: String): ImageDetailSideEffect()
}