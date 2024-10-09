package com.easyhz.patchnote.ui.screen.home.contract

import com.easyhz.patchnote.core.common.base.UiIntent

sealed class HomeIntent: UiIntent() {
    data class FetchData(val searchParam: List<String>?): HomeIntent()
    data object NavigateToDataManagement: HomeIntent()
    data object NavigateToDefectEntry: HomeIntent()
    data object NavigateToFilter: HomeIntent()
}