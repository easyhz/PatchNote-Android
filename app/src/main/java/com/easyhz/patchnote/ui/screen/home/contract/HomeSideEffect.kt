package com.easyhz.patchnote.ui.screen.home.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect

sealed class HomeSideEffect: UiSideEffect() {
    data object NavigateToDataManagement: HomeSideEffect()
    data object NavigateToDefectEntry: HomeSideEffect()
    data object NavigateToFilter: HomeSideEffect()
}