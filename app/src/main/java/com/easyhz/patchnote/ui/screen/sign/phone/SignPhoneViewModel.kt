package com.easyhz.patchnote.ui.screen.sign.phone

import android.app.Activity
import android.content.Context
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.handleError
import com.easyhz.patchnote.data.model.sign.param.RequestVerificationCodeParam
import com.easyhz.patchnote.data.model.sign.response.RequestVerificationCodeResponse
import com.easyhz.patchnote.domain.usecase.sign.RequestVerificationCodeUseCase
import com.easyhz.patchnote.ui.screen.sign.phone.contract.PhoneIntent
import com.easyhz.patchnote.ui.screen.sign.phone.contract.PhoneSideEffect
import com.easyhz.patchnote.ui.screen.sign.phone.contract.PhoneState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignPhoneViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val requestVerificationCodeUseCase: RequestVerificationCodeUseCase,
): BaseViewModel<PhoneState, PhoneIntent, PhoneSideEffect>(
    PhoneState.init()
){
    override fun handleIntent(intent: PhoneIntent) {
        when(intent) {
            is PhoneIntent.ChangePhoneText -> { changePhoneText(intent.text) }
            is PhoneIntent.NavigateToUp -> { navigateToUp() }
            is PhoneIntent.RequestVerificationCode -> { requestVerificationCode(intent.activity) }
        }
    }

    private fun changePhoneText(text: String) {
        reduce { copy(phoneText = text.filter { it.isDigit() }, enabledButton = text.isNotBlank()) }
    }

    private fun navigateToUp() {
        postSideEffect { PhoneSideEffect.NavigateToUp }
    }

    private fun requestVerificationCode(activity: Activity) = viewModelScope.launch {
        setLoading(true)
        val param = RequestVerificationCodeParam(currentState.phoneText, activity)
        requestVerificationCodeUseCase(param).onSuccess { response ->
            when(response) {
                is RequestVerificationCodeResponse.ReturnUid -> {
                    postSideEffect { PhoneSideEffect.NavigateToSignName(response.uid, response.phoneNumber) }
                }
                is RequestVerificationCodeResponse.ReturnCodeSent -> {
                    postSideEffect { PhoneSideEffect.NavigateToSignVerification(response.verificationId, response.phoneNumber) }
                }
            }
        }.onFailure { e ->
            showSnackBar(context, e.handleError()) {
                PhoneSideEffect.ShowSnackBar(it)
            }
        }.also {
            setLoading(false)
        }
    }

    private fun setLoading(isLoading: Boolean) {
        reduce { copy(isLoading = isLoading) }
    }
}