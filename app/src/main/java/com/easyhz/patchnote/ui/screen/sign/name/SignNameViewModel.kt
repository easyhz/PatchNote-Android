package com.easyhz.patchnote.ui.screen.sign.name

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.handleError
import com.easyhz.patchnote.core.common.util.resource.ResourceHelper
import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.domain.usecase.sign.SaveUserUseCase
import com.easyhz.patchnote.ui.screen.sign.name.contract.NameIntent
import com.easyhz.patchnote.ui.screen.sign.name.contract.NameSideEffect
import com.easyhz.patchnote.ui.screen.sign.name.contract.NameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignNameViewModel @Inject constructor(
    private val resourceHelper: ResourceHelper,
    private val savedStateHandle: SavedStateHandle,
    private val saveUserUseCase: SaveUserUseCase,
): BaseViewModel<NameState, NameIntent, NameSideEffect>(
    initialState = NameState.init()
) {
    override fun handleIntent(intent: NameIntent) {
        when(intent) {
            is NameIntent.ChangeNameText -> { changeNameText(intent.text) }
            is NameIntent.NavigateToUp -> { navigateToUp() }
            is NameIntent.ClickNextButton -> { saveUser() }
        }
    }

    init {
        init()
    }

    private fun init() {
        val uid: String? = savedStateHandle["uid"]
        val phoneNumber: String? = savedStateHandle["phoneNumber"]
        if (uid.isNullOrBlank() || phoneNumber.isNullOrBlank()) {
            navigateToUp()
            return
        }
        reduce { copy(uid = uid, phoneNumber = phoneNumber) }
    }


    private fun changeNameText(text: String) {
        reduce { copy(nameText = text, enabledButton = text.isNotBlank()) }
    }

    private fun saveUser() {
        viewModelScope.launch {
            setLoading(true)
            val userRequest = User(
                id = currentState.uid,
                phone = currentState.phoneNumber,
                name = currentState.nameText,
                currentTeamId = null,
                teamIds = emptyList(),
                teamJoinDates = emptyList()
            )
            saveUserUseCase.invoke(userRequest).onSuccess {
                navigateToTeam()
            }.onFailure { e ->
                showSnackBar(resourceHelper, e.handleError()) {
                    NameSideEffect.ShowSnackBar(it)
                }
            }.also {
                setLoading(false)
            }
        }
    }

    private fun navigateToUp() {
        postSideEffect { NameSideEffect.NavigateToUp }
    }

    private fun navigateToTeam() {
        postSideEffect { NameSideEffect.NavigateToTeam }
    }

    private fun setLoading(isLoading: Boolean) {
        reduce { copy(isLoading = isLoading) }
    }
}