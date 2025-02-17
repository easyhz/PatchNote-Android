package com.easyhz.patchnote.ui.screen.offline.defect.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.configuration.Configuration

data class OfflineDefectState(
    val appConfiguration: Configuration,
    val isRefreshing: Boolean,
    val isShowOnboardingDialog: Boolean,
): UiState() {
    companion object {
        fun init() = OfflineDefectState(
            isRefreshing = false,
            isShowOnboardingDialog = false,
            appConfiguration = Configuration("", "", ""),
        )
    }
}
