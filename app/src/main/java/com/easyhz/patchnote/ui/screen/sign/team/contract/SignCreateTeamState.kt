package com.easyhz.patchnote.ui.screen.sign.team.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.user.User

data class SignCreateTeamState(
    val user: User,
    val teamNameText: String,
    val enabledButton: Boolean,
    val isLoading: Boolean,
    val isShowTeamDialog: Boolean,
): UiState() {
    companion object {
        fun init() = SignCreateTeamState(
            user = User.Empty,
            teamNameText = "",
            enabledButton = false,
            isLoading = true,
            isShowTeamDialog = false,
        )
    }
}
