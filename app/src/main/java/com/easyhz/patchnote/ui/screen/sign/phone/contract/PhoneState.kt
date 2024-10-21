package com.easyhz.patchnote.ui.screen.sign.phone.contract

import com.easyhz.patchnote.core.common.base.UiState

data class PhoneState(
    val phoneText: String,
    val enabledButton: Boolean,
    val isLoading: Boolean
): UiState() {
    companion object {
        fun init() = PhoneState(
            phoneText = "010",
            enabledButton = false,
            isLoading = false
        )
    }
}