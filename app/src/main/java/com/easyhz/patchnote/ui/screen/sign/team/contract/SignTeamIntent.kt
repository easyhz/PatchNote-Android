package com.easyhz.patchnote.ui.screen.sign.team.contract

import com.easyhz.patchnote.core.common.base.UiIntent

sealed class SignTeamIntent: UiIntent() {
    data class ChangeTeamNameText(val text: String): SignTeamIntent()
    data object NavigateToUp: SignTeamIntent()
}