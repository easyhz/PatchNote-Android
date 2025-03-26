package com.easyhz.patchnote.ui.screen.setting.team.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect

/**
 * Date: 2025. 3. 26.
 * Time: 오후 2:20
 */

sealed class TeamInformationSideEffect : UiSideEffect() {
    data object NavigateUp: TeamInformationSideEffect()
    data object NavigateToDataManagement: TeamInformationSideEffect()
    data object NavigateToMember: TeamInformationSideEffect()
    data object NavigateToSplash: TeamInformationSideEffect()
    data class CopyTeamInviteCode(val inviteCode: String): TeamInformationSideEffect()
    data class ShowSnackBar(val value: String): TeamInformationSideEffect()
}