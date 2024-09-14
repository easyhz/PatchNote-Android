package com.easyhz.patchnote.ui.screen.sign.phone.contract

import com.easyhz.patchnote.core.common.base.UiState

data class PhoneState(
    val phoneText: String,
    val enabledButton: Boolean,
): UiState() {
    companion object {
        fun init() = PhoneState(
            phoneText = "",
            enabledButton = false
        )
    }
}