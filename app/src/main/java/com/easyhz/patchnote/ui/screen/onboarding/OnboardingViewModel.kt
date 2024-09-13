package com.easyhz.patchnote.ui.screen.onboarding

import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.ui.screen.onboarding.contract.OnboardingIntent
import com.easyhz.patchnote.ui.screen.onboarding.contract.OnboardingSideEffect
import com.easyhz.patchnote.ui.screen.onboarding.contract.OnboardingState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(

): BaseViewModel<OnboardingState, OnboardingIntent, OnboardingSideEffect>(
    OnboardingState
) {
    override fun handleIntent(intent: OnboardingIntent) {
        when(intent) {
            OnboardingIntent.NavigateToSign -> {
                navigateToSign()
            }
        }
    }

    private fun navigateToSign() {
        postSideEffect { OnboardingSideEffect.NavigateToSign }
    }
}