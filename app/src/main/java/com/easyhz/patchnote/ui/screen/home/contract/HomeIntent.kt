package com.easyhz.patchnote.ui.screen.home.contract

import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.core.model.filter.FilterParam

sealed class HomeIntent: UiIntent() {
    data class FetchData(val filterParam: FilterParam): HomeIntent()
    data class Refresh(val filterParam: FilterParam): HomeIntent()
    data object ClickSetting: HomeIntent()
    data object ClickExport: HomeIntent()
    data object NavigateToDefectEntry: HomeIntent()
    data object NavigateToFilter: HomeIntent()
    data class NavigateToDefectDetail(val defectId: String): HomeIntent()
    data object UpdateAppVersion: HomeIntent()
    data object NavigateToNotion: HomeIntent()
    data class ChangePasswordText(val newValue: String): HomeIntent()
    data object CheckPassword: HomeIntent()
    data object HidePasswordDialog: HomeIntent()
    data object HidePasswordErrorDialog: HomeIntent()
    data object ExportData: HomeIntent()
    data object HideExportDialog: HomeIntent()
    data object ShowExportDialog: HomeIntent()
    data class SetLoading(val value: Boolean): HomeIntent()
}