package com.easyhz.patchnote.ui.screen.sign.vericiation.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect

sealed class VerificationSideEffect: UiSideEffect() {
    data object NavigateToUp: VerificationSideEffect()
    data class NavigateToName(val uid: String, val phoneNumber: String): VerificationSideEffect()
    data object NavigateToHome: VerificationSideEffect()
    data class ShowSnackBar(val value: String): VerificationSideEffect()
}