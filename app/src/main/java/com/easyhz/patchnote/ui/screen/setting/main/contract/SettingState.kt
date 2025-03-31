package com.easyhz.patchnote.ui.screen.setting.main.contract

import com.easyhz.patchnote.core.common.base.UiState

data class SettingState(
    val teamName: String,
    val isInputDialogVisible: Boolean,
    val blockInputDialogText: String,
    val isSuccessDialogVisible: Boolean
): UiState() {
    companion object {
        fun init() = SettingState(
            teamName = "",
            isInputDialogVisible = false,
            blockInputDialogText = "",
            isSuccessDialogVisible = false
        )
    }
}