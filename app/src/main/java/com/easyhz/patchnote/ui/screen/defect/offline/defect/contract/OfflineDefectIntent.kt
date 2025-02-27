package com.easyhz.patchnote.ui.screen.defect.offline.defect.contract

import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.core.model.defect.DefectItem

sealed class OfflineDefectIntent: UiIntent() {
    data object Refresh: OfflineDefectIntent()
    data object ClickSetting: OfflineDefectIntent()
    data class NavigateToOfflineDefectDetail(val defectItem: DefectItem): OfflineDefectIntent()
    data object HideOnboardingDialog: OfflineDefectIntent()
    data object ClickTopBarName: OfflineDefectIntent()
    data object ClickAllUpload: OfflineDefectIntent()
    data object UploadAllOfflineDefect: OfflineDefectIntent()
    data object HideUploadDialog: OfflineDefectIntent()
    data object HideUploadSuccessDialog: OfflineDefectIntent()
}