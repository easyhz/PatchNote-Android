package com.easyhz.patchnote.ui.screen.team.selection.contract

import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.core.model.team.Team

sealed class TeamSelectionIntent: UiIntent() {
    data class SelectTeam(val team: Team): TeamSelectionIntent()
    data object NavigateUp: TeamSelectionIntent()
    data object NavigateToTeamSign: TeamSelectionIntent()
}