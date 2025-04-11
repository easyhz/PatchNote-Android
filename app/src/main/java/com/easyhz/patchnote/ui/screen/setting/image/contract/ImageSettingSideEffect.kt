package com.easyhz.patchnote.ui.screen.setting.image.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect

/**
 * Date: 2025. 4. 11.
 * Time: 오후 10:12
 */

sealed class ImageSettingSideEffect : UiSideEffect() {
    data object NavigateUp: ImageSettingSideEffect()
}