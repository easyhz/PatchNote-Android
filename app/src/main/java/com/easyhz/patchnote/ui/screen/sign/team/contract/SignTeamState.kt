package com.easyhz.patchnote.ui.screen.sign.team.contract

import com.easyhz.patchnote.core.common.base.UiState

data class SignTeamState(
    val uid: String,
    val phoneNumber: String,
    val userName: String,
    val teamText: String,
    val enabledButton: Boolean,
    val isLoading: Boolean,
): UiState() {
    companion object {
        fun init() = SignTeamState(
            uid = "",
            phoneNumber = "",
            userName = "",
            teamText = "",
            enabledButton = false,
            isLoading = false
        )
    }
}
