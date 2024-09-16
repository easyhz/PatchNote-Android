package com.easyhz.patchnote.ui.screen.sign.name

import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.data.model.sign.request.SaveUserRequest
import com.easyhz.patchnote.domain.usecase.sign.SaveUserUseCase
import com.easyhz.patchnote.ui.screen.sign.name.contract.NameIntent
import com.easyhz.patchnote.ui.screen.sign.name.contract.NameSideEffect
import com.easyhz.patchnote.ui.screen.sign.name.contract.NameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignNameViewModel @Inject constructor(
    private val saveUserUseCase: SaveUserUseCase
): BaseViewModel<NameState, NameIntent, NameSideEffect>(
    initialState = NameState.init()
) {
    override fun handleIntent(intent: NameIntent) {
        when(intent) {
            is NameIntent.ChangeNameText -> { changeNameText(intent.text) }
            is NameIntent.NavigateToUp -> { navigateToUp() }
            is NameIntent.SaveUser -> { saveUser(intent.uid, intent.phoneNumber) }
        }
    }

    private fun changeNameText(text: String) {
        reduce { copy(nameText = text, enabledButton = text.isNotBlank()) }
    }

    private fun navigateToUp() {
        postSideEffect { NameSideEffect.NavigateToUp }
    }

    private fun saveUser(uid: String, phoneNumber: String) = viewModelScope.launch {
        val userRequest = SaveUserRequest(
            id = uid,
            name = currentState.nameText,
            phone = phoneNumber
        )
        saveUserUseCase.invoke(userRequest).onSuccess {
            postSideEffect { NameSideEffect.NavigateToHome }
            println("성공")
        }.onFailure {
            println(">> 실패 $it")
        }
    }
}