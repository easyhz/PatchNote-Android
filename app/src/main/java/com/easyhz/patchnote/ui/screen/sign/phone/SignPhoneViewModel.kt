package com.easyhz.patchnote.ui.screen.sign.phone

import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.ui.screen.sign.phone.contract.PhoneIntent
import com.easyhz.patchnote.ui.screen.sign.phone.contract.PhoneSideEffect
import com.easyhz.patchnote.ui.screen.sign.phone.contract.PhoneState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignPhoneViewModel @Inject constructor(

): BaseViewModel<PhoneState, PhoneIntent, PhoneSideEffect>(
    PhoneState.init()
){
    override fun handleIntent(intent: PhoneIntent) {
        when(intent) {
            is PhoneIntent.ChangePhoneText -> { changePhoneText(intent.text) }
            is PhoneIntent.NavigateToUp -> { navigateToUp() }
        }
    }

    private fun changePhoneText(text: String) {
        reduce { copy(phoneText = text) }
    }

    private fun navigateToUp() {
        postSideEffect { PhoneSideEffect.NavigateToUp }
    }
}