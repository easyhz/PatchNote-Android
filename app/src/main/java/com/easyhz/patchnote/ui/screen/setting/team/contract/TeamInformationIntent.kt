package com.easyhz.patchnote.ui.screen.setting.team.contract

import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.core.model.error.DialogMessage
import com.easyhz.patchnote.core.model.setting.TeamItem

/**
 * Date: 2025. 3. 26.
 * Time: 오후 2:20
 */

sealed class TeamInformationIntent : UiIntent() {
    data object NavigateUp: TeamInformationIntent()
    data class ClickTeamInformationItem(val teamItem: TeamItem): TeamInformationIntent()
    data class ShowError(val message: DialogMessage?): TeamInformationIntent()

}