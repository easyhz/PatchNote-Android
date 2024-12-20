package com.easyhz.patchnote.ui.screen.setting.main.contract

import com.easyhz.patchnote.core.common.base.UiState

data class SettingState(
    val isInputDialogVisible: Boolean,
    val blockInputDialogText: String,
    val isSuccessDialogVisible: Boolean
): UiState() {
    companion object {
        fun init() = SettingState(
            isInputDialogVisible = false,
            blockInputDialogText = "",
            isSuccessDialogVisible = false
        )
    }
}