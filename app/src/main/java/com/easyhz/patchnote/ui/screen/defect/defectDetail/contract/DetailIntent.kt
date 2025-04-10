package com.easyhz.patchnote.ui.screen.defect.defectDetail.contract

import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.core.designSystem.util.bottomSheet.DefectDetailBottomSheet
import com.easyhz.patchnote.core.model.error.DialogMessage

sealed class DetailIntent: UiIntent() {
    data object NavigateToUp: DetailIntent()
    data object CompleteDefect: DetailIntent()
    data class ChangeStateBottomSheet(val isShowBottomSheet: Boolean): DetailIntent()
    data class ClickBottomSheetItem(val bottomSheetType: DefectDetailBottomSheet): DetailIntent()
    data class ShowError(val message: DialogMessage?): DetailIntent()
    data object DeleteDefect: DetailIntent()
    data class ShowDeleteDialog(val isShow: Boolean): DetailIntent()
    data class SetLoading(val isLoading: Boolean): DetailIntent()
    data class ClickImage(val imageIndex: Int, val tabIndex: Int): DetailIntent()
}