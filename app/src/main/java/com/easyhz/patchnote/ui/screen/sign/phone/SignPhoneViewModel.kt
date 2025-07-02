package com.easyhz.patchnote.ui.screen.sign.phone

import android.app.Activity
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.handleError
import com.easyhz.patchnote.core.common.util.resource.ResourceHelper
import com.easyhz.patchnote.data.model.sign.param.RequestVerificationCodeParam
import com.easyhz.patchnote.data.model.sign.response.RequestVerificationCodeResponse
import com.easyhz.patchnote.domain.usecase.sign.RequestVerificationCodeUseCase
import com.easyhz.patchnote.ui.screen.sign.phone.contract.PhoneIntent
import com.easyhz.patchnote.ui.screen.sign.phone.contract.PhoneSideEffect
import com.easyhz.patchnote.ui.screen.sign.phone.contract.PhoneState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignPhoneViewModel @Inject constructor(
    private val resourceHelper: ResourceHelper,
    private val requestVerificationCodeUseCase: RequestVerificationCodeUseCase,
): BaseViewModel<PhoneState, PhoneIntent, PhoneSideEffect>(
    PhoneState.init()
){
    override fun handleIntent(intent: PhoneIntent) {
        when(intent) {
            is PhoneIntent.ChangePhoneText -> { changePhoneText(intent.text) }
            is PhoneIntent.NavigateToUp -> { navigateToUp() }
            is PhoneIntent.RequestVerificationCode -> { requestVerificationCode() }
        }
    }

    private fun changePhoneText(text: String) {
        reduce { copy(phoneText = text.filter { it.isDigit() }, enabledButton = text.isNotBlank()) }
    }

    private fun navigateToUp() {
        postSideEffect { PhoneSideEffect.NavigateToUp }
    }

    private fun requestVerificationCode() = viewModelScope.launch {
        setLoading(true)
        val param = RequestVerificationCodeParam(currentState.phoneText)
        requestVerificationCodeUseCase(param).onSuccess { response ->
            postSideEffect { PhoneSideEffect.NavigateToSignVerification(phoneNumber = response, ) }
        }.onFailure { e ->
            showSnackBar(resourceHelper, e.handleError()) {
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