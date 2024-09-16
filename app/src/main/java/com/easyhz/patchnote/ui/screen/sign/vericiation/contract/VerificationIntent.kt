package com.easyhz.patchnote.ui.screen.sign.vericiation.contract

import com.easyhz.patchnote.core.common.base.UiIntent

sealed class VerificationIntent: UiIntent() {
    data class ChangeVerificationCodeText(val text: String): VerificationIntent()
    data object NavigateToUp: VerificationIntent()
    data class RequestVerification(val verificationId: String, val phoneNumber: String): VerificationIntent()
}