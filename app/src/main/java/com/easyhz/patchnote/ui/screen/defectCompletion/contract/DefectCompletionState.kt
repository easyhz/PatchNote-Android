package com.easyhz.patchnote.ui.screen.defectCompletion.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.error.DialogMessage
import com.easyhz.patchnote.core.model.image.DefectImage

data class DefectCompletionState(
    val completionContent: String,
    val images: List<DefectImage>,
    val isShowImageBottomSheet: Boolean,
    val isShowDialog: Boolean = false,
    val dialogMessage: DialogMessage?,
    val isLoading: Boolean,
): UiState() {
    companion object {
        fun init() = DefectCompletionState(
            completionContent = "",
            images = emptyList(),
            isShowImageBottomSheet = false,
            isShowDialog = false,
            dialogMessage = null,
            isLoading = false,
        )

        fun DefectCompletionState.updateImages(newImages: List<DefectImage>): DefectCompletionState =
            this.copy(images = this.images + newImages)

        fun DefectCompletionState.deleteImage(image: DefectImage): DefectCompletionState =
            this.copy(images = this.images.filter { it.id != image.id })
    }

}
