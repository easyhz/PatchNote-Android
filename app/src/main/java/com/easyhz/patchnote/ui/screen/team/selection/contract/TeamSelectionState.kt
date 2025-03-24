package com.easyhz.patchnote.ui.screen.team.selection.contract

import com.easyhz.patchnote.core.common.base.UiState

data class TeamSelectionState(
    val isLoading: Boolean,
): UiState() {
    companion object {
        fun init(): TeamSelectionState = TeamSelectionState(
            isLoading = true
        )
    }
}
