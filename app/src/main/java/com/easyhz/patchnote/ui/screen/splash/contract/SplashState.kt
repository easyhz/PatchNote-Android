package com.easyhz.patchnote.ui.screen.splash.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.configuration.Configuration

data class SplashState(
    val appConfiguration: Configuration,
    val needsUpdate: Boolean,
): UiState() {
    companion object {
        fun init() = SplashState(
            appConfiguration = Configuration("", "", ""),
            needsUpdate = false
        )
    }
}