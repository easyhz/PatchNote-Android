package com.easyhz.patchnote.ui.screen.sign.name

import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.ui.screen.sign.name.contract.NameIntent
import com.easyhz.patchnote.ui.screen.sign.name.contract.NameSideEffect
import com.easyhz.patchnote.ui.screen.sign.name.contract.NameState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignNameViewModel @Inject constructor(

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

    private fun navigateToTeam() {
        postSideEffect { NameSideEffect.NavigateToTeam(currentState.nameText) }
    }

    private fun setLoading(isLoading: Boolean) {
        reduce { copy(isLoading = isLoading) }
    }
}