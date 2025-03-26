package com.easyhz.patchnote.ui.screen.team.member.contract

import com.easyhz.patchnote.core.common.base.UiState

/**
 * Date: 2025. 3. 26.
 * Time: 오후 4:26
 */

data class TeamMemberState(
    val isLoading: Boolean
) : UiState() {
    companion object {
        fun init(): TeamMemberState = TeamMemberState(
            isLoading = true
        )
    }
}
            