package com.easyhz.patchnote.ui.screen.home.contract

import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.filter.FilterParam

sealed class HomeIntent: UiIntent() {
    data class FetchData(val filterParam: FilterParam): HomeIntent()
    data class Refresh(val filterParam: FilterParam): HomeIntent()
    data object ClickSetting: HomeIntent()
    data object ClickExport: HomeIntent()
    data object NavigateToDefectEntry: HomeIntent()
    data object NavigateToFilter: HomeIntent()
    data class NavigateToDefectDetail(val defectItem: DefectItem): HomeIntent()
    data class SetLoading(val value: Boolean): HomeIntent()
    data object HideOnboardingDialog: HomeIntent()
    data object ShowOnboardingDialog: HomeIntent()
}