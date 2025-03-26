package com.easyhz.patchnote.ui.screen.team.member.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.user.User

/**
 * Date: 2025. 3. 26.
 * Time: 오후 4:26
 */

data class TeamMemberState(
    val isLoading: Boolean,
    val members: List<User>,
) : UiState() {
    companion object {
        fun init(): TeamMemberState = TeamMemberState(
            isLoading = true,
            members = emptyList()
        )
    }
}
            