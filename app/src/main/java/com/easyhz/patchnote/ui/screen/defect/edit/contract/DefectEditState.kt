package com.easyhz.patchnote.ui.screen.defect.edit.contract

import androidx.compose.ui.text.input.TextFieldValue
import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.error.DialogMessage
import com.easyhz.patchnote.core.model.image.DefectImage

data class DefectEditState(
    val isLoading: Boolean,
    val defectItem: DefectItem?,
    val entryContent: String,
    val images: List<DefectImage>,
    val isSuccessGetData: Boolean,
    val isShowImageBottomSheet: Boolean,
    val dialogMessage: DialogMessage?,
    val isShowEntryDialog: Boolean,
    val entryItem: LinkedHashMap<CategoryType, TextFieldValue>,
): UiState() {
    companion object {
        fun init() = DefectEditState(
            isLoading = true,
            defectItem = null,
            entryContent = "",
            images = emptyList(),
            isShowImageBottomSheet = false,
            dialogMessage = null,
            isShowEntryDialog = false,
            isSuccessGetData = false,
            entryItem = linkedMapOf()
        )

        fun DefectEditState.updateImages(newImages: List<DefectImage>): DefectEditState =
            this.copy(images = this.images + newImages)

        fun DefectEditState.deleteImage(image: DefectImage): DefectEditState =
            this.copy(images = this.images.filter { it.id != image.id })
    }
}