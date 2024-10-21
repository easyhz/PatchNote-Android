package com.easyhz.patchnote.ui.screen.sign.phone.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect

sealed class PhoneSideEffect: UiSideEffect() {
    data object NavigateToUp: PhoneSideEffect()
    data class NavigateToSignVerification(val verificationId: String, val phoneNumber: String): PhoneSideEffect()
    data class NavigateToSignName(val uid: String, val phoneNumber: String): PhoneSideEffect()
    data class ShowSnackBar(val message: String): PhoneSideEffect()
}