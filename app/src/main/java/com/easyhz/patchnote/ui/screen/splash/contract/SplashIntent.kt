package com.easyhz.patchnote.ui.screen.splash.contract

import com.easyhz.patchnote.core.common.base.UiIntent

sealed class SplashIntent: UiIntent() {
    data object UpdateAppVersion: SplashIntent()
}