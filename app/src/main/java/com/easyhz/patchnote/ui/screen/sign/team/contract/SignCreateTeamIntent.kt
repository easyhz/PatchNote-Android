package com.easyhz.patchnote.ui.screen.sign.team.contract

import com.easyhz.patchnote.core.common.base.UiIntent

sealed class SignCreateTeamIntent: UiIntent() {
    data class ChangeTeamNameText(val text: String): SignCreateTeamIntent()
    data object ClickMainButton: SignCreateTeamIntent()
    data object NavigateToUp: SignCreateTeamIntent()
}