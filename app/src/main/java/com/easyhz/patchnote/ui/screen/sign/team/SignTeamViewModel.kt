package com.easyhz.patchnote.ui.screen.sign.team

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.handleError
import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.domain.usecase.sign.SaveUserUseCase
import com.easyhz.patchnote.domain.usecase.team.FindTeamByCodeUseCase
import com.easyhz.patchnote.ui.screen.sign.name.contract.NameSideEffect
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
): BaseViewModel<SignTeamState, SignTeamIntent, SignTeamSideEffect>(
    initialState = SignTeamState.init()
){
    override fun handleIntent(intent: SignTeamIntent) {
        TODO("Not yet implemented")
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

    private fun findTeamByCode() = viewModelScope.launch {
        setLoading(true)
        findTeamByCodeUseCase.invoke(currentState.teamText).onSuccess {
            // 다이얼로그 띄우기
        }.onFailure { e ->
            showSnackBar(context, e.handleError()) {
                SignTeamSideEffect.ShowSnackBar(it)
            }
        }.also {
            setLoading(false)
        }
    }

    private fun saveUser() = viewModelScope.launch {
        setLoading(true)
        val userRequest = User(
            id = currentState.uid,
            name = currentState.userName,
            phone = currentState.phoneNumber
        )
        saveUserUseCase.invoke(userRequest).onSuccess {
//            postSideEffect { NameSideEffect.NavigateToHome }
        }.onFailure { e ->
            showSnackBar(context, e.handleError()) {
                SignTeamSideEffect.ShowSnackBar(it)
            }
        }.also {
            setLoading(false)
        }
    }

    private fun navigateToUp() {
//        postSideEffect { SignTeamSideEffect.NavigateToUp }
    }

    private fun setLoading(isLoading: Boolean) {
        reduce { copy(isLoading = isLoading) }
    }
}