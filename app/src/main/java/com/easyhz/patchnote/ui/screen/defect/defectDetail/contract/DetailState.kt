package com.easyhz.patchnote.ui.screen.defect.defectDetail.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.error.DialogMessage

data class DetailState(
    val isLoading: Boolean,
    val defectItem: DefectItem?,
    val isOwner: Boolean,
    val isShowBottomSheet: Boolean,
    val isShowDeleteDialog: Boolean,
    val dialogMessage: DialogMessage?,
): UiState() {
    companion object {
        fun init() = DetailState(
            isLoading = false,
            defectItem = null,
            isOwner = false,
            isShowBottomSheet = false,
            isShowDeleteDialog = false,
            dialogMessage = null,
        )
    }
}
