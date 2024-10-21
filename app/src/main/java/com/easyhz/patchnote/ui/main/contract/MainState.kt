package com.easyhz.patchnote.ui.main.contract

import com.easyhz.patchnote.core.common.base.UiState

data class MainState(
    val isLogin: Boolean,
    val isLoading: Boolean
): UiState() {
    companion object {
        fun init() = MainState(
            isLogin = true,
            isLoading = true
        )
    }
}
