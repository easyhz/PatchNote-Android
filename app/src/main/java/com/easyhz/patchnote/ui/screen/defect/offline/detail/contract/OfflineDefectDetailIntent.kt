package com.easyhz.patchnote.ui.screen.defect.offline.detail.contract

import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.core.designSystem.util.bottomSheet.DefectDetailBottomSheet
import com.easyhz.patchnote.core.model.error.DialogMessage

sealed class OfflineDefectDetailIntent: UiIntent() {
    data object NavigateToUp: OfflineDefectDetailIntent()
    data class ChangeStateBottomSheet(val isShowBottomSheet: Boolean): OfflineDefectDetailIntent()
    data class ClickBottomSheetItem(val bottomSheetType: DefectDetailBottomSheet): OfflineDefectDetailIntent()
    data class ShowError(val message: DialogMessage?): OfflineDefectDetailIntent()
    data object DeleteDefect: OfflineDefectDetailIntent()
    data object HideUploadSuccessDialog: OfflineDefectDetailIntent()
    data object ClickMainButton: OfflineDefectDetailIntent()
    data class ShowDeleteDialog(val isShow: Boolean): OfflineDefectDetailIntent()
    data object HideUploadDialog: OfflineDefectDetailIntent()
    data object UploadOfflineDefect: OfflineDefectDetailIntent()
    data class SetLoading(val isLoading: Boolean): OfflineDefectDetailIntent()
}