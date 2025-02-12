package com.easyhz.patchnote.ui.screen.defectEntry.contract

import androidx.compose.ui.text.input.TextFieldValue
import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.error.DialogMessage
import com.easyhz.patchnote.core.model.image.DefectImage

data class DefectEntryState(
    val isLoading: Boolean,
    val entryContent: String,
    val images: List<DefectImage>,
    val isShowImageBottomSheet: Boolean,
    val dialogMessage: DialogMessage?,
    val isShowEntryDialog: Boolean,
    val entryItem: LinkedHashMap<CategoryType, TextFieldValue>,
) : UiState() {
    companion object {
        fun init() = DefectEntryState(
            isLoading = true,
            entryContent = "",
            images = emptyList(),
            isShowImageBottomSheet = false,
            dialogMessage = null,
            isShowEntryDialog = false,
            entryItem = linkedMapOf()
        )

        fun DefectEntryState.updateImages(newImages: List<DefectImage>): DefectEntryState =
            this.copy(images = this.images + newImages)

        fun DefectEntryState.deleteImage(image: DefectImage): DefectEntryState =
            this.copy(images = this.images.filter { it.id != image.id })
    }
}
