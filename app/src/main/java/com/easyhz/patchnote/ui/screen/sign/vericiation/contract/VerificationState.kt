package com.easyhz.patchnote.ui.screen.sign.vericiation.contract

import com.easyhz.patchnote.core.common.base.UiState

data class VerificationState(
    val codeText: String,
    val enabledButton: Boolean,
): UiState() {
    companion object {
        fun init() = VerificationState(
            codeText = "",
            enabledButton = false
        )
    }
}