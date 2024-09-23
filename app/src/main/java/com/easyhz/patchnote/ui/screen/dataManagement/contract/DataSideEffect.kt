package com.easyhz.patchnote.ui.screen.dataManagement.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect

sealed class DataSideEffect: UiSideEffect() {
    data object NavigateToDataEntry: DataSideEffect()
    data object NavigateToUp: DataSideEffect()
}