package com.easyhz.patchnote.ui.screen.sign.vericiation

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.handleError
import com.easyhz.patchnote.core.model.sign.SignType
import com.easyhz.patchnote.data.model.sign.param.SignInWithPhoneParam
import com.easyhz.patchnote.domain.usecase.sign.CheckAlreadyUserUseCase
import com.easyhz.patchnote.domain.usecase.sign.SignInWithPhoneUseCase
import com.easyhz.patchnote.ui.screen.sign.vericiation.contract.VerificationIntent
import com.easyhz.patchnote.ui.screen.sign.vericiation.contract.VerificationSideEffect
import com.easyhz.patchnote.ui.screen.sign.vericiation.contract.VerificationState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignVerificationViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val signInWithPhoneUseCase: SignInWithPhoneUseCase,
    private val checkAlreadyUserUseCase: CheckAlreadyUserUseCase,
): BaseViewModel<VerificationState, VerificationIntent, VerificationSideEffect>(
    initialState = VerificationState.init()
) {
    override fun handleIntent(intent: VerificationIntent) {
        when(intent) {
            is VerificationIntent.ChangeVerificationCodeText -> { changeVerificationCodeText(intent.text) }
            is VerificationIntent.NavigateToUp -> { navigateToUp() }
            is VerificationIntent.RequestVerification -> { requestVerification(intent.verificationId, intent.phoneNumber) }
        }
    }

    private fun changeVerificationCodeText(text: String) {
        reduce { copy(codeText = text.filter { it.isDigit() }, enabledButton = text.isNotBlank()) }
    }

    private fun navigateToUp() {
        postSideEffect { VerificationSideEffect.NavigateToUp }
    }

    private fun requestVerification(verificationId: String, phoneNumber: String) = viewModelScope.launch {
        setLoading(true)
        val param = SignInWithPhoneParam(verificationId, currentState.codeText)
        signInWithPhoneUseCase.invoke(param).onSuccess {
            checkAlreadyUser(it, phoneNumber)
        }.onFailure { e ->
            showSnackBar(context, e.handleError()) {
                VerificationSideEffect.ShowSnackBar(it)
            }
        }
    }

    private fun checkAlreadyUser(uid: String, phoneNumber: String) = viewModelScope.launch {
        checkAlreadyUserUseCase.invoke(uid).onSuccess { signType ->
            when(signType) {
                is SignType.NewUser -> navigateToName(uid = uid, phoneNumber = phoneNumber)
                is SignType.TeamRequired -> navigateToTeam(uid = signType.uid, phoneNumber = signType.phoneNumber, userName = signType.userName)
                is SignType.ExistingUser -> navigateToHome()
            }
        }.onFailure { e ->
            showSnackBar(context, e.handleError()) {
                VerificationSideEffect.ShowSnackBar(it)
            }
        }.also {
            setLoading(false)
        }

    }

    private fun navigateToName(uid: String, phoneNumber: String) {
        postSideEffect { VerificationSideEffect.NavigateToName(uid = uid, phoneNumber = phoneNumber) }
    }

    private fun navigateToHome() {
        postSideEffect { VerificationSideEffect.NavigateToHome }
    }

    private fun navigateToTeam(uid: String, phoneNumber: String, userName: String) {
        postSideEffect { VerificationSideEffect.NavigateToTeam(uid = uid, phoneNumber = phoneNumber, userName = userName) }
    }

    private fun setLoading(isLoading: Boolean) {
        reduce { copy(isLoading = isLoading) }
    }
}