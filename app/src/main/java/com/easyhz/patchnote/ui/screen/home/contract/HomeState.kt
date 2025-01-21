package com.easyhz.patchnote.ui.screen.home.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.configuration.Configuration

data class HomeState(
    val appConfiguration: Configuration,
    val isLatestVersion: Boolean,
    val isRefreshing: Boolean,
    val isShowPasswordDialog: Boolean,
    val isShowPasswordErrorDialog: Boolean,
    val hasPassword: Boolean,
    val password: String,
    val isLoading: Boolean
): UiState() {
    companion object {
        fun init() = HomeState(
            appConfiguration = Configuration("", "", ""),
            isLatestVersion = true,
            isRefreshing = false,
            isShowPasswordDialog = false,
            isShowPasswordErrorDialog = false,
            hasPassword = false,
            password = "",
            isLoading = false
        )
    }
}
