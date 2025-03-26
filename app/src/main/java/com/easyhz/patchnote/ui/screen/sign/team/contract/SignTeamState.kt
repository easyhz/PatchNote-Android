package com.easyhz.patchnote.ui.screen.sign.team.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.user.User

data class SignTeamState(
    val user: User,
    val teamCodeText: String,
    val teamName: String,
    val teamId: String,
    val enabledButton: Boolean,
    val isLoading: Boolean,
    val isShowTeamDialog: Boolean,
): UiState() {
    companion object {
        fun init() = SignTeamState(
            user = User.Empty,
            teamCodeText = "",
            teamName = "",
            teamId = "",
            enabledButton = false,
            isLoading = true,
            isShowTeamDialog = false,
        )
    }
}
