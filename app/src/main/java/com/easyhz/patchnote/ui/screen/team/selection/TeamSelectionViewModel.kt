package com.easyhz.patchnote.ui.screen.team.selection

import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.util.log.Logger
import com.easyhz.patchnote.core.model.team.Team
import com.easyhz.patchnote.domain.usecase.team.FetchTeamsUseCase
import com.easyhz.patchnote.domain.usecase.team.SaveCurrentTeamUseCase
import com.easyhz.patchnote.ui.screen.team.selection.contract.TeamSelectionIntent
import com.easyhz.patchnote.ui.screen.team.selection.contract.TeamSelectionSideEffect
import com.easyhz.patchnote.ui.screen.team.selection.contract.TeamSelectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamSelectionViewModel @Inject constructor(
    private val logger: Logger,
    private val fetchTeamsUseCase: FetchTeamsUseCase,
    private val saveCurrentTeamUseCase: SaveCurrentTeamUseCase,
): BaseViewModel<TeamSelectionState, TeamSelectionIntent, TeamSelectionSideEffect>(
    initialState = TeamSelectionState.init()
) {
    private val tag = "TeamSelectionViewModel"
    override fun handleIntent(intent: TeamSelectionIntent) {
        when (intent) {
            is TeamSelectionIntent.SelectTeam -> {
                selectTeam(intent.team)
            }
            is TeamSelectionIntent.NavigateUp -> {
                navigateToUp()
            }
            is TeamSelectionIntent.NavigateToTeamSign -> {
                navigateToTeamSign()
            }
        }
    }

    init {
        fetchTeams()
    }

    private fun fetchTeams() {
        viewModelScope.launch {
            fetchTeamsUseCase(Unit).onSuccess {
                reduce { copy(teams = it) }
            }.onFailure { e ->
                handleError(message = "Error fetching teams", e = e)
            }.also {
                setLoading(false)
            }
        }
    }

    private fun selectTeam(team: Team) {
        viewModelScope.launch {
            setLoading(true)
            saveCurrentTeamUseCase.invoke(team).onSuccess {
                navigateToHome()
            }.onFailure { e ->
                handleError(message = "Error saving team", e = e)
            }.also {
                setLoading(false)
            }
        }
    }

    private fun navigateToUp() {
        postSideEffect { TeamSelectionSideEffect.NavigateUp }
    }

    private fun navigateToTeamSign() {
        postSideEffect { TeamSelectionSideEffect.NavigateToTeamSign }
    }

    private fun navigateToHome() {
        postSideEffect { TeamSelectionSideEffect.NavigateToHome }
    }

    private fun setLoading(isLoading: Boolean) {
        reduce { copy(isLoading = isLoading) }
    }

    private fun handleError(message: String, e: Throwable) {
        logger.e(tag, message, e)
    }
}