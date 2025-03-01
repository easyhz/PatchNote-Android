package com.easyhz.patchnote.ui.screen.defect.offline.defect.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect
import com.easyhz.patchnote.core.model.defect.DefectItem

sealed class OfflineDefectSideEffect: UiSideEffect() {
    data object NavigateToSetting: OfflineDefectSideEffect()
    data object NavigateToLogin: OfflineDefectSideEffect()
    data class NavigateToOfflineDefectDetail(val defectItem: DefectItem): OfflineDefectSideEffect()
}