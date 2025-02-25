package com.easyhz.patchnote.ui.screen.home.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.configuration.Configuration

data class HomeState(
    val teamName: String,
    val isRefreshing: Boolean,
    val isLoading: Boolean,
    val isShowOnboardingDialog: Boolean,
): UiState() {
    companion object {
        fun init() = HomeState(
            teamName = "PatchNote",
            isRefreshing = false,
            isLoading = false,
            isShowOnboardingDialog = false
        )
    }
}
