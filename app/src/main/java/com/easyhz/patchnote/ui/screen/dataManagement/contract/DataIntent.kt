package com.easyhz.patchnote.ui.screen.dataManagement.contract

import com.easyhz.patchnote.core.common.base.UiIntent

sealed class DataIntent: UiIntent() {
    data class DeleteCategoryValue(val categoryIndex: Int, val index: Int): DataIntent()
    data object NavigateToDataEntry: DataIntent()
    data object NavigateToUp: DataIntent()
    data object ClickPositiveButton: DataIntent()
    data object HideDeleteDialog: DataIntent()
}