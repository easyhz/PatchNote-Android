package com.easyhz.patchnote.ui.screen.team.selection.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect

sealed class TeamSelectionSideEffect: UiSideEffect() {
    data object NavigateUp: TeamSelectionSideEffect()
    data object NavigateToSignTeam: TeamSelectionSideEffect()
    data object NavigateToHome: TeamSelectionSideEffect()
}