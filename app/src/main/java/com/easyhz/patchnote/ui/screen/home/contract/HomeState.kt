package com.easyhz.patchnote.ui.screen.home.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.configuration.Configuration

data class HomeState(
    val teamName: String,
    val appConfiguration: Configuration,
    val needsUpdate: Boolean,
    val isRefreshing: Boolean,
    val isShowPasswordDialog: Boolean,
    val isShowPasswordErrorDialog: Boolean,
    val hasPassword: Boolean,
    val password: String,
    val isLoading: Boolean,
    val isShowOnboardingDialog: Boolean,
): UiState() {
    companion object {
        fun init() = HomeState(
            teamName = "PatchNote",
            appConfiguration = Configuration("", "", ""),
            needsUpdate = true,
            isRefreshing = false,
            isShowPasswordDialog = false,
            isShowPasswordErrorDialog = false,
            hasPassword = false,
            password = "",
            isLoading = false,
            isShowOnboardingDialog = false
        )
    }
}
