package com.easyhz.patchnote.ui.screen.sign.vericiation

import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.data.model.sign.param.SignInWithPhoneParam
import com.easyhz.patchnote.domain.usecase.SignInWithPhoneUseCase
import com.easyhz.patchnote.ui.screen.sign.vericiation.contract.VerificationIntent
import com.easyhz.patchnote.ui.screen.sign.vericiation.contract.VerificationSideEffect
import com.easyhz.patchnote.ui.screen.sign.vericiation.contract.VerificationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignVerificationViewModel @Inject constructor(
    private val signInWithPhoneUseCase: SignInWithPhoneUseCase,
): BaseViewModel<VerificationState, VerificationIntent, VerificationSideEffect>(
    initialState = VerificationState.init()
) {
    override fun handleIntent(intent: VerificationIntent) {
        when(intent) {
            is VerificationIntent.ChangeVerificationCodeText -> { changeVerificationCodeText(intent.text) }
            is VerificationIntent.NavigateToUp -> { navigateToUp() }
            is VerificationIntent.RequestVerification -> { requestVerification(intent.verificationId) }
        }
    }

    private fun changeVerificationCodeText(text: String) {
        reduce { copy(codeText = text, enabledButton = text.isNotBlank()) }
    }

    private fun navigateToUp() {
        postSideEffect { VerificationSideEffect.NavigateToUp }
    }

    private fun requestVerification(verificationId: String) = viewModelScope.launch {
        val param = SignInWithPhoneParam(verificationId, currentState.codeText)
        signInWithPhoneUseCase.invoke(param).onSuccess {
            println(">>> 성공 $it")
        }.onFailure {
            println(">>> 실패 $it")
        }
    }
}