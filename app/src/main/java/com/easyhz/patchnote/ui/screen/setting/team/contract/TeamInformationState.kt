package com.easyhz.patchnote.ui.screen.setting.team.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.error.DialogMessage
import com.easyhz.patchnote.core.model.team.TeamInformation

/**
 * Date: 2025. 3. 26.
 * Time: 오후 2:20
 */

data class TeamInformationState(
    val teamInformation: TeamInformation,
    val dialogMessage: DialogMessage?,
    val isLoading: Boolean
) : UiState() {
    companion object {
        fun init(): TeamInformationState = TeamInformationState(
            teamInformation = TeamInformation.Empty,
            dialogMessage = null,
            isLoading = true,
        )
    }
}
            