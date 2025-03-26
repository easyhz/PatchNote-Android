package com.easyhz.patchnote.ui.screen.team.selection.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.team.Team

data class TeamSelectionState(
    val isLoading: Boolean,
    val teams: List<Team>,
): UiState() {
    companion object {
        fun init(): TeamSelectionState = TeamSelectionState(
            isLoading = true,
            teams = emptyList(),
        )
    }
}
