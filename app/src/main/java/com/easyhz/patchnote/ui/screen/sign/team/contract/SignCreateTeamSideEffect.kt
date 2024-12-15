package com.easyhz.patchnote.ui.screen.sign.team.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect

sealed class SignCreateTeamSideEffect: UiSideEffect() {
    data class ShowSnackBar(val message: String): SignCreateTeamSideEffect()
    data object NavigateToHome: SignCreateTeamSideEffect()
    data object NavigateToUp: SignCreateTeamSideEffect()
}