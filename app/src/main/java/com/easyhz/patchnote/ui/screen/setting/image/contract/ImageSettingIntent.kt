package com.easyhz.patchnote.ui.screen.setting.image.contract

import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.core.model.image.DisplayImageType

/**
 * Date: 2025. 4. 11.
 * Time: 오후 10:12
 */

sealed class ImageSettingIntent : UiIntent() {
    data object NavigateUp: ImageSettingIntent()
    data class ClickToggleButton(val displayImageType: DisplayImageType, val newValue: Boolean): ImageSettingIntent()
}