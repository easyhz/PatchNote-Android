package com.easyhz.patchnote.ui.screen.sign.phone.contract

import com.easyhz.patchnote.core.common.base.UiIntent

sealed class PhoneIntent: UiIntent() {
    data class ChangePhoneText(val text: String): PhoneIntent()
    data object NavigateToUp: PhoneIntent()
}