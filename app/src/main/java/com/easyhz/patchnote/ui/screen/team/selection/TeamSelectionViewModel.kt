package com.easyhz.patchnote.ui.screen.team.selection

import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.ui.screen.team.selection.contract.TeamSelectionIntent
import com.easyhz.patchnote.ui.screen.team.selection.contract.TeamSelectionSideEffect
import com.easyhz.patchnote.ui.screen.team.selection.contract.TeamSelectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TeamSelectionViewModel @Inject constructor(

): BaseViewModel<TeamSelectionState, TeamSelectionIntent, TeamSelectionSideEffect>(
    initialState = TeamSelectionState.init()
) {
    override fun handleIntent(intent: TeamSelectionIntent) {
        TODO("Not yet implemented")
    }
}