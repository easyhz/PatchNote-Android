package com.easyhz.patchnote.ui.screen.defect.offline.detail.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.error.DialogMessage

data class OfflineDefectDetailState(
    val isLoading: Boolean,
    val defectItem: DefectItem?,
    val isShowBottomSheet: Boolean,
    val isShowUploadDialog: Boolean,
    val isShowDeleteDialog: Boolean,
    val isShowUploadSuccessDialog: Boolean,
    val dialogMessage: DialogMessage?,
): UiState() {
    companion object {
        fun init() = OfflineDefectDetailState(
            isLoading = false,
            defectItem = null,
            isShowBottomSheet = false,
            isShowUploadDialog = false,
            isShowDeleteDialog = false,
            isShowUploadSuccessDialog = false,
            dialogMessage = null,
        )
    }
}
