package com.easyhz.patchnote.ui.screen.sign.team.contract

import com.easyhz.patchnote.core.common.base.UiState

data class SignCreateTeamState(
    val uid: String,
    val phoneNumber: String,
    val userName: String,
    val teamNameText: String,
    val enabledButton: Boolean,
    val isLoading: Boolean,
    val isShowTeamDialog: Boolean,
): UiState() {
    companion object {
        fun init() = SignCreateTeamState(
            uid = "",
            phoneNumber = "",
            userName = "",
            teamNameText = "",
            enabledButton = false,
            isLoading = false,
            isShowTeamDialog = false,
        )
    }
}
