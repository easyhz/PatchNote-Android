package com.easyhz.patchnote.ui.screen.offline.defect.contract

import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.core.model.defect.DefectItem

sealed class OfflineDefectIntent: UiIntent() {
    data object Refresh: OfflineDefectIntent()
    data object ClickSetting: OfflineDefectIntent()
    data class NavigateToOfflineDefectDetail(val defectItem: DefectItem): OfflineDefectIntent()
    data object HideOnboardingDialog: OfflineDefectIntent()
    data object ClickTopBarName: OfflineDefectIntent()
    data object ClickUpload: OfflineDefectIntent()
}