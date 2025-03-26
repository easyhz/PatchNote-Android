package com.easyhz.patchnote.ui.screen.sign.team

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.handleError
import com.easyhz.patchnote.core.common.util.Generate
import com.easyhz.patchnote.core.model.team.CreateTeamParam
import com.easyhz.patchnote.core.model.team.Team
import com.easyhz.patchnote.core.model.user.TeamJoinDate
import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.domain.usecase.team.CreateTeamUseCase
import com.easyhz.patchnote.domain.usecase.team.UpdateTeamNameUseCase
import com.easyhz.patchnote.domain.usecase.user.GetUserUseCase
import com.easyhz.patchnote.ui.screen.sign.team.contract.SignCreateTeamIntent
import com.easyhz.patchnote.ui.screen.sign.team.contract.SignCreateTeamSideEffect
import com.easyhz.patchnote.ui.screen.sign.team.contract.SignCreateTeamState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class SignCreateTeamViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getUserUseCase: GetUserUseCase,
    private val createTeamUseCase: CreateTeamUseCase,
    private val updateTeamNameUseCase: UpdateTeamNameUseCase,
): BaseViewModel<SignCreateTeamState, SignCreateTeamIntent, SignCreateTeamSideEffect>(
    initialState = SignCreateTeamState.init()
) {
    override fun handleIntent(intent: SignCreateTeamIntent) {
        when(intent) {
            is SignCreateTeamIntent.ChangeTeamNameText -> changeTeamNameText(intent.text)
            is SignCreateTeamIntent.ClickMainButton -> createTeam()
            is SignCreateTeamIntent.NavigateToUp -> navigateToUp()
        }
    }

    init {
        init()
    }

    private fun init() {
        viewModelScope.launch {
            getUserUseCase.invoke(Unit).onSuccess {
                reduce { copy(user = it) }
            }.onFailure {
                navigateToUp()
            }.also {
                setLoading(false)
            }
        }
    }

    private fun changeTeamNameText(text: String) {
        reduce { copy(teamNameText = text, enabledButton = text.isNotBlank()) }
    }

    private fun createTeam() {
        viewModelScope.launch {
            setLoading(true)
            val teamId = Generate.randomUUID()
            val param = CreateTeamParam(
                user = getUser(teamId),
                team = getTeam(teamId)
            )
            createTeamUseCase.invoke(param).onSuccess {
                updateTeamNameUseCase.invoke(currentState.teamNameText)
                navigateToTeamSelection()
            }.onFailure { e ->
                showSnackBar(context, e.handleError()) {
                    SignCreateTeamSideEffect.ShowSnackBar(it)
                }
            }.also {
                setLoading(false)
            }
        }
    }

    private fun getUser(teamId: String): User {
        return currentState.user.copy(
            teamIds = currentState.user.teamIds + teamId,
            teamJoinDates = currentState.user.teamJoinDates + TeamJoinDate.create(teamId = teamId)
        )
    }

    private fun getTeam(teamId: String): Team {
        return Team(
            id = teamId,
            name = currentState.teamNameText,
            adminId = currentState.user.id,
            inviteCode = Generate.randomInviteCode(),
            creationTime = LocalDateTime.now()
        )
    }

    private fun navigateToUp() {
        postSideEffect { SignCreateTeamSideEffect.NavigateToUp }
    }

    private fun navigateToTeamSelection() {
        postSideEffect { SignCreateTeamSideEffect.NavigateToTeamSelection }
    }

    private fun setLoading(isLoading: Boolean) {
        reduce { copy(isLoading = isLoading) }
    }
}