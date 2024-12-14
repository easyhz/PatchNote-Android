package com.easyhz.patchnote.ui.screen.sign.team.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect

sealed class SignTeamSideEffect: UiSideEffect() {
    data class ShowSnackBar(val message: String): SignTeamSideEffect()
}