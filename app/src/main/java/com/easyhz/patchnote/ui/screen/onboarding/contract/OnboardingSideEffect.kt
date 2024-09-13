package com.easyhz.patchnote.ui.screen.onboarding.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect

sealed class OnboardingSideEffect: UiSideEffect() {
    data object NavigateToSign: OnboardingSideEffect()
}