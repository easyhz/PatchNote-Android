package com.easyhz.patchnote.ui.screen.sign.name.contract

import com.easyhz.patchnote.core.common.base.UiState

data class NameState(
    val nameText: String,
    val enabledButton: Boolean,
    val isLoading: Boolean
): UiState() {
    companion object {
        fun init() = NameState(
            nameText = "",
            enabledButton = false,
            isLoading = false
        )
    }
}