package com.easyhz.patchnote.ui.screen.image.detail.contract

import android.app.Activity
import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.ui.screen.image.detail.component.ImageDetailBottomBarType

/**
 * Date: 2025. 4. 9.
 * Time: 오후 10:05
 */

sealed class ImageDetailIntent : UiIntent() {
    data object NavigateUp : ImageDetailIntent()
    data class ClickDisplayButton(val isChecked: Boolean) : ImageDetailIntent()
    data class ClickSaveButton(val imageDetailBottomBarType: ImageDetailBottomBarType, val currentImage: Int, val activity: Activity) : ImageDetailIntent()
    data class ChangedCurrentImage(val currentImage: Int) : ImageDetailIntent()
    data object ToggleShowTopBar: ImageDetailIntent()
}