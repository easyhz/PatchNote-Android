package com.easyhz.patchnote.ui.screen.sign.team.contract

import com.easyhz.patchnote.core.common.base.UiIntent

sealed class SignTeamIntent: UiIntent() {
    data class ChangeTeamCodeText(val text: String): SignTeamIntent()
    data object NavigateToUp: SignTeamIntent()
    data object RequestTeamCheck: SignTeamIntent()
    data object ClickPositiveButton: SignTeamIntent()
    data object HideTeamDialog: SignTeamIntent()
    data object NavigateToCreateTeam: SignTeamIntent()
}