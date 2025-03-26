package com.easyhz.patchnote.ui.screen.team.member

import com.easyhz.patchnote.core.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.easyhz.patchnote.ui.screen.team.member.contract.TeamMemberIntent
import com.easyhz.patchnote.ui.screen.team.member.contract.TeamMemberSideEffect
import com.easyhz.patchnote.ui.screen.team.member.contract.TeamMemberState

/**
 * Date: 2025. 3. 26.
 * Time: 오후 4:26
 */

@HiltViewModel
class TeamMemberViewModel @Inject constructor(

) : BaseViewModel<TeamMemberState, TeamMemberIntent, TeamMemberSideEffect>(
    initialState = TeamMemberState.init()
) {
    override fun handleIntent(intent: TeamMemberIntent) {
        TODO("Not yet implemented")
        when (intent) {

        }
    }
}