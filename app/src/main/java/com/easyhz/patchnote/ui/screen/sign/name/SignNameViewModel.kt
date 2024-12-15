package com.easyhz.patchnote.ui.screen.sign.name

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.handleError
import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.domain.usecase.sign.SaveUserUseCase
import com.easyhz.patchnote.ui.screen.sign.name.contract.NameIntent
import com.easyhz.patchnote.ui.screen.sign.name.contract.NameSideEffect
import com.easyhz.patchnote.ui.screen.sign.name.contract.NameState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignNameViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val saveUserUseCase: SaveUserUseCase
): BaseViewModel<NameState, NameIntent, NameSideEffect>(
    initialState = NameState.init()
) {
    override fun handleIntent(intent: NameIntent) {
        when(intent) {
            is NameIntent.ChangeNameText -> { changeNameText(intent.text) }
            is NameIntent.NavigateToUp -> { navigateToUp() }
            is NameIntent.NavigateToTeam -> { navigateToTeam() }
        }
    }

    private fun changeNameText(text: String) {
        reduce { copy(nameText = text, enabledButton = text.isNotBlank()) }
    }

    private fun navigateToUp() {
        postSideEffect { NameSideEffect.NavigateToUp }
    }

    private fun saveUser(uid: String, phoneNumber: String) = viewModelScope.launch {
        setLoading(true)
        val userRequest = User(
            id = uid,
            name = currentState.nameText,
            phone = phoneNumber,
            teamId = ""
        )
        saveUserUseCase.invoke(userRequest).onSuccess {
//            postSideEffect { NameSideEffect.NavigateToHome }
        }.onFailure { e ->
            showSnackBar(context, e.handleError()) {
                NameSideEffect.ShowSnackBar(it)
            }
        }.also {
            setLoading(false)
        }
    }

    private fun navigateToTeam() {
        postSideEffect { NameSideEffect.NavigateToTeam(currentState.nameText) }
    }

    private fun setLoading(isLoading: Boolean) {
        reduce { copy(isLoading = isLoading) }
    }
}