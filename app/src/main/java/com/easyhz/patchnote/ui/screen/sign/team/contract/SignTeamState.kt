package com.easyhz.patchnote.ui.screen.sign.team.contract

import com.easyhz.patchnote.core.common.base.UiState

data class SignTeamState(
    val uid: String,
    val phoneNumber: String,
    val userName: String,
    val teamCodeText: String,
    val teamName: String,
    val teamId: String,
    val enabledButton: Boolean,
    val isLoading: Boolean,
    val isShowTeamDialog: Boolean,
): UiState() {
    companion object {
        fun init() = SignTeamState(
            uid = "",
            phoneNumber = "",
            userName = "",
            teamCodeText = "",
            teamName = "",
            teamId = "",
            enabledButton = false,
            isLoading = false,
            isShowTeamDialog = false,
        )
    }
}
