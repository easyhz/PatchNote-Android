package com.easyhz.patchnote.ui.screen.image.detail.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.image.DisplayImageType

/**
 * Date: 2025. 4. 9.
 * Time: 오후 10:05
 */

data class ImageDetailState(
    val isLoading: Boolean,
    val isDisplayInformation: Boolean,
    val defectItem: DefectItem?,
    val images: List<String>,
    val currentImage: Int,
    val isShowTopBar: Boolean,
    val settingOption: LinkedHashMap<DisplayImageType, Boolean>
) : UiState() {
    companion object {
        fun init(): ImageDetailState = ImageDetailState(
            isLoading = true,
            isDisplayInformation = true,
            defectItem = null,
            images = emptyList(),
            currentImage = 0,
            isShowTopBar = true,
            settingOption = DisplayImageType.default()
        )
    }
}
            