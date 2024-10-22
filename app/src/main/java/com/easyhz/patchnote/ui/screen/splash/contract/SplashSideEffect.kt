package com.easyhz.patchnote.ui.screen.splash.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect

sealed class SplashSideEffect: UiSideEffect() {
    data object NavigateToHome: SplashSideEffect()
    data object NavigateToOnboarding: SplashSideEffect()
}