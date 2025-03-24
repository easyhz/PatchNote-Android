package com.easyhz.patchnote.ui.screen.sign.team

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.handleError
import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.domain.usecase.sign.GetUserUseCase
import com.easyhz.patchnote.domain.usecase.sign.SaveUserUseCase
import com.easyhz.patchnote.domain.usecase.team.FindTeamByCodeUseCase
import com.easyhz.patchnote.domain.usecase.team.UpdateTeamNameUseCase
import com.easyhz.patchnote.ui.screen.sign.team.contract.SignTeamIntent
import com.easyhz.patchnote.ui.screen.sign.team.contract.SignTeamSideEffect
import com.easyhz.patchnote.ui.screen.sign.team.contract.SignTeamState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignTeamViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val savedStateHandle: SavedStateHandle,
    private val findTeamByCodeUseCase: FindTeamByCodeUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateTeamNameUseCase: UpdateTeamNameUseCase,
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
        val uid: String? = savedStateHandle["uid"]
        val phoneNumber: String? = savedStateHandle["phoneNumber"]
        val userName: String? = savedStateHandle["userName"]
        if (uid.isNullOrBlank() || phoneNumber.isNullOrBlank() || userName.isNullOrBlank()) {
            getUser()
        } else {
            reduce { copy(uid = uid, phoneNumber = phoneNumber, userName = userName) }
        }
    }

    private fun getUser() = viewModelScope.launch {
        getUserUseCase.invoke(Unit).onSuccess {
            reduce { copy(uid = it.id, phoneNumber = it.phone, userName = it.name) }
        }.onFailure {
            navigateToUp()
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
            showSnackBar(context, e.handleError()) {
                SignTeamSideEffect.ShowSnackBar(it)
            }
        }.also {
            setLoading(false)
        }
    }

    private fun saveUser() = viewModelScope.launch {
        hideTeamDialog()
        setLoading(true)
        val userRequest = User(
            id = currentState.uid,
            name = currentState.userName,
            phone = currentState.phoneNumber,
            currentTeamId = null,
            teamIds = listOf(currentState.teamId),
        )
        saveUserUseCase.invoke(userRequest).onSuccess {
            updateTeamNameUseCase.invoke(currentState.teamName)
            navigateToTeamSelection()
        }.onFailure { e ->
            showSnackBar(context, e.handleError()) {
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