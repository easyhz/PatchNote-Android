package com.easyhz.patchnote.ui.screen.home.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect
import com.easyhz.patchnote.core.model.defect.DefectItem

sealed class HomeSideEffect: UiSideEffect() {
    data object NavigateToSetting: HomeSideEffect()
    data object NavigateToDefectEntry: HomeSideEffect()
    data object NavigateToFilter: HomeSideEffect()
    data object NavigateToExport: HomeSideEffect()
    data object NavigateToLogin: HomeSideEffect()
    data class NavigateToDefectDetail(val defectItem: DefectItem): HomeSideEffect()
}