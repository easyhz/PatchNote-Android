package com.easyhz.patchnote.ui.screen.sign.name.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect

sealed class NameSideEffect: UiSideEffect() {
    data object NavigateToUp: NameSideEffect()
    data object NavigateToHome: NameSideEffect()
    data class ShowSnackBar(val message: String): NameSideEffect()
}