package com.easyhz.patchnote.ui.screen.onboarding.contract

import com.easyhz.patchnote.core.common.base.UiIntent

sealed class OnboardingIntent: UiIntent() {
    data object NavigateToSign: OnboardingIntent()
}