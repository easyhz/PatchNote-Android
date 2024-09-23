package com.easyhz.patchnote.ui.screen.dataEntry.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect

sealed class DataEntrySideEffect: UiSideEffect() {
    data object NavigateToUp: DataEntrySideEffect()
}