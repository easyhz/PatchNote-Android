package com.easyhz.patchnote.ui.screen.sign.name.contract

import com.easyhz.patchnote.core.common.base.UiState

data class NameState(
    val uid: String,
    val phoneNumber: String,
    val nameText: String,
    val enabledButton: Boolean,
    val isLoading: Boolean
): UiState() {
    companion object {
        fun init() = NameState(
            uid = "",
            phoneNumber = "",
            nameText = "",
            enabledButton = false,
            isLoading = false
        )
    }
}