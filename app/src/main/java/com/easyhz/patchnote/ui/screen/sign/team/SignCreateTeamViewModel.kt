package com.easyhz.patchnote.ui.screen.sign.team

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.AppError
import com.easyhz.patchnote.core.common.error.handleError
import com.easyhz.patchnote.core.common.util.Generate
import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.domain.usecase.sign.SaveUserUseCase
import com.easyhz.patchnote.ui.screen.sign.team.contract.SignCreateTeamIntent
import com.easyhz.patchnote.ui.screen.sign.team.contract.SignCreateTeamSideEffect
import com.easyhz.patchnote.ui.screen.sign.team.contract.SignCreateTeamState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignCreateTeamViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val savedStateHandle: SavedStateHandle,
    private val saveUserUseCase: SaveUserUseCase,
): BaseViewModel<SignCreateTeamState, SignCreateTeamIntent, SignCreateTeamSideEffect>(
    initialState = SignCreateTeamState.init()
) {
    override fun handleIntent(intent: SignCreateTeamIntent) {
        when(intent) {
            is SignCreateTeamIntent.ChangeTeamNameText -> changeTeamNameText(intent.text)
            is SignCreateTeamIntent.ClickMainButton -> saveInformation()
            is SignCreateTeamIntent.NavigateToUp -> navigateToUp()
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
            navigateToUp()
            return
        }
        reduce { copy(uid = uid, phoneNumber = phoneNumber, userName = userName) }
    }

    private fun changeTeamNameText(text: String) {
        reduce { copy(teamNameText = text, enabledButton = text.isNotBlank()) }
    }

    private fun saveInformation() {
        viewModelScope.launch {
            setLoading(true)
            try {
                val results = awaitAll(
                    async { runCatching { saveTeam() } },
                    async { runCatching { saveUser() } }
                )

                handleResults(results)
            } catch (e: Exception) {
                showSnackBar(context, e.handleError()) {
                    SignCreateTeamSideEffect.ShowSnackBar(it)
                }
            } finally {
                setLoading(false)
            }
        }
    }

    private suspend fun saveTeam() {
        throw NotImplementedError("Not implemented")
    }

    private suspend fun saveUser() {
        val userRequest = User(
            id = currentState.uid,
            name = currentState.userName,
            phone = currentState.phoneNumber,
            teamId = Generate.randomUUID()
        )
        return saveUserUseCase.invoke(userRequest).getOrThrow()
    }


    private fun navigateToUp() {
        postSideEffect { SignCreateTeamSideEffect.NavigateToUp }
    }

    private fun navigateToHome() {
        postSideEffect { SignCreateTeamSideEffect.NavigateToHome }
    }

    private fun setLoading(isLoading: Boolean) {
        reduce { copy(isLoading = isLoading) }
    }

    private fun handleResults(results: List<Result<*>>) {
        val combinedException = results
            .mapNotNull { it.exceptionOrNull() }
            .onEach { Log.e(this.javaClass.name, "handleResults error", it) }
            .takeIf { it.isNotEmpty() }
            ?.let { AppError.UnexpectedError }

        if (combinedException == null) return

        throw combinedException
    }
}