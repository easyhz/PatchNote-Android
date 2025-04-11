package com.easyhz.patchnote.ui.screen.setting.image.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.image.DisplayImageType

/**
 * Date: 2025. 4. 11.
 * Time: 오후 10:12
 */

data class ImageSettingState(
    val isLoading: Boolean,
    val items: LinkedHashMap<DisplayImageType, Boolean>
) : UiState() {
    companion object {
        fun init(): ImageSettingState = ImageSettingState(
            isLoading = true,
            items = linkedMapOf(
                DisplayImageType.SITE to true,
                DisplayImageType.BUILDING_UNIT to true,
                DisplayImageType.SPACE to true,
                DisplayImageType.PART to false,
                DisplayImageType.WORK_TYPE to false,
                DisplayImageType.REQUEST to true,
                DisplayImageType.COMPLETION to false,
            )
        )
    }
}
            