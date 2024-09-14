package com.easyhz.patchnote.ui.screen.sign.vericiation.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect

sealed class VerificationSideEffect: UiSideEffect() {
    data object NavigateToUp: VerificationSideEffect()
    data object NavigateToName: VerificationSideEffect()
}