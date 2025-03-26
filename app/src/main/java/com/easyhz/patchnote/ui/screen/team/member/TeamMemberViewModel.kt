package com.easyhz.patchnote.ui.screen.team.member

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.handleError
import com.easyhz.patchnote.core.common.util.log.Logger
import com.easyhz.patchnote.domain.usecase.team.FetchTeamMemberUseCase
import com.easyhz.patchnote.ui.screen.team.member.contract.TeamMemberIntent
import com.easyhz.patchnote.ui.screen.team.member.contract.TeamMemberSideEffect
import com.easyhz.patchnote.ui.screen.team.member.contract.TeamMemberState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Date: 2025. 3. 26.
 * Time: 오후 4:26
 */

@HiltViewModel
class TeamMemberViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val logger: Logger,
    private val fetchTeamMemberUseCase: FetchTeamMemberUseCase,
) : BaseViewModel<TeamMemberState, TeamMemberIntent, TeamMemberSideEffect>(
    initialState = TeamMemberState.init()
) {
    private val tag = "TeamMemberViewModel"
    override fun handleIntent(intent: TeamMemberIntent) {
        when (intent) {
            is TeamMemberIntent.NavigateUp -> navigateUp()
        }
    }

    init {
        fetchTeamMember()
    }

    private fun fetchTeamMember() {
        viewModelScope.launch {
            fetchTeamMemberUseCase(Unit).onSuccess {
                reduce { copy(members = it) }
            }.onFailure { e ->
                logger.e(tag, "fetchTeamMember : ${e.message}", e)
                showSnackBar(context, e.handleError()) {
                    TeamMemberSideEffect.ShowSnackBar(it)
                }
            }.also {
                reduce { copy(isLoading = false) }
            }
        }
    }

    private fun navigateUp() {
        postSideEffect { TeamMemberSideEffect.NavigateUp }
    }
}