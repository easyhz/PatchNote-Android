package com.easyhz.patchnote.ui.screen.splash.contract

import com.easyhz.patchnote.BuildConfig
import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.error.DialogMessage

data class SplashState(
    val dialogMessage: DialogMessage?,
    val version: String,
): UiState() {
    companion object {
        fun init() = SplashState(
            dialogMessage = null,
            version = "v${BuildConfig.VERSION_NAME}"
        )
    }
}