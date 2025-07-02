package com.easyhz.patchnote.ui.screen.sign.team

import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.handleError
import com.easyhz.patchnote.core.common.util.resource.ResourceHelper
import com.easyhz.patchnote.domain.usecase.sign.SaveUserUseCase
import com.easyhz.patchnote.domain.usecase.team.FindTeamByCodeUseCase
import com.easyhz.patchnote.domain.usecase.user.GetUserUseCase
import com.easyhz.patchnote.ui.screen.sign.team.contract.SignTeamIntent
import com.easyhz.patchnote.ui.screen.sign.team.contract.SignTeamSideEffect
import com.easyhz.patchnote.ui.screen.sign.team.contract.SignTeamState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignTeamViewModel @Inject constructor(
    private val resourceHelper: ResourceHelper,
    private val findTeamByCodeUseCase: FindTeamByCodeUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val getUserUseCase: GetUserUseCase,
): BaseViewModel<SignTeamState, SignTeamIntent, SignTeamSideEffect>(
    initialState = SignTeamState.init()
) {
    override fun handleIntent(intent: SignTeamIntent) {
        when(intent) {
            is SignTeamIntent.ChangeTeamCodeText -> changeTeamCodeText(intent.text)
            is SignTeamIntent.NavigateToUp -> navigateToUp()
            is SignTeamIntent.RequestTeamCheck -> findTeamByCode()
            is SignTeamIntent.ClickPositiveButton -> saveUser()
            is SignTeamIntent.HideTeamDialog -> hideTeamDialog()
            is SignTeamIntent.NavigateToCreateTeam -> navigateToCreateTeam()
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

    private fun changeTeamCodeText(text: String) {
        reduce { copy(teamCodeText = text, enabledButton = text.isNotBlank()) }
    }


    private fun findTeamByCode() = viewModelScope.launch {
        setLoading(true)
        findTeamByCodeUseCase.invoke(currentState.teamCodeText).onSuccess {
            reduce { copy(teamName = it.name, teamId = it.id) }
            setDialog(true)
        }.onFailure { e ->
            showSnackBar(resourceHelper, e.handleError()) {
                SignTeamSideEffect.ShowSnackBar(it)
            }
        }.also {
            setLoading(false)
        }
    }

    private fun saveUser() = viewModelScope.launch {
        hideTeamDialog()
        setLoading(true)
        val teamId = currentState.teamId
        val userRequest = currentState.user.copy(
            // TODO 고치세요
//            teamIds = currentState.user.teamIds + teamId,
//            teamJoinDates = currentState.user.teamJoinDates + TeamJoinDate.create(teamId = teamId)
        )
        saveUserUseCase.invoke(userRequest).onSuccess {
            navigateToTeamSelection()
        }.onFailure { e ->
            showSnackBar(resourceHelper, e.handleError()) {
                SignTeamSideEffect.ShowSnackBar(it)
            }
        }.also {
            setLoading(false)
        }
    }

    private fun hideTeamDialog() {
        setDialog(false)
    }

    private fun navigateToUp() {
        postSideEffect { SignTeamSideEffect.NavigateToUp }
    }

    private fun navigateToTeamSelection() {
        postSideEffect { SignTeamSideEffect.NavigateToTeamSelection }
    }

    private fun navigateToCreateTeam() {
        postSideEffect { SignTeamSideEffect.NavigateToCreateTeam }
    }

    private fun setDialog(isShow: Boolean) {
        reduce { copy(isShowTeamDialog = isShow) }
    }

    private fun setLoading(isLoading: Boolean) {
        reduce { copy(isLoading = isLoading) }
    }
}