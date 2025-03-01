package com.easyhz.patchnote.ui.screen.defect.offline.defect.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.configuration.Configuration
import com.easyhz.patchnote.core.model.defect.OfflineDefectProgress

data class OfflineDefectState(
    val appConfiguration: Configuration,
    val isRefreshing: Boolean,
    val isShowOnboardingDialog: Boolean,
    val isShowUploadDialog: Boolean,
    val isShowUploadSuccessDialog: Boolean,
    val isShowUploadProgressDialog: Boolean,
    val uploadProgress: Float,
    val offlineDefectProgress: OfflineDefectProgress?,
): UiState() {
    companion object {
        fun init() = OfflineDefectState(
            isRefreshing = false,
            isShowOnboardingDialog = false,
            appConfiguration = Configuration("", "", ""),
            isShowUploadDialog = false,
            isShowUploadSuccessDialog = false,
            isShowUploadProgressDialog = false,
            uploadProgress = 0f,
            offlineDefectProgress = null
        )
    }
}
