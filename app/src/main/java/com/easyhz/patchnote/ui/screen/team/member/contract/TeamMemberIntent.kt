package com.easyhz.patchnote.ui.screen.team.member.contract

import com.easyhz.patchnote.core.common.base.UiIntent

/**
 * Date: 2025. 3. 26.
 * Time: 오후 4:26
 */

sealed class TeamMemberIntent : UiIntent() {
    data object NavigateUp : TeamMemberIntent()
}