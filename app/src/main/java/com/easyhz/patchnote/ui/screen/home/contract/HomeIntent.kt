package com.easyhz.patchnote.ui.screen.home.contract

import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.core.model.filter.FilterParam

sealed class HomeIntent: UiIntent() {
    data class FetchData(val filterParam: FilterParam): HomeIntent()
    data object NavigateToDataManagement: HomeIntent()
    data object NavigateToDefectEntry: HomeIntent()
    data object NavigateToFilter: HomeIntent()
    data class NavigateToDefectDetail(val defectId: String): HomeIntent()
}