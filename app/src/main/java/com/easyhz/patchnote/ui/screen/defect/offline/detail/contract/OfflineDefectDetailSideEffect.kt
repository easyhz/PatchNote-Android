package com.easyhz.patchnote.ui.screen.defect.offline.detail.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect
import com.easyhz.patchnote.core.model.defect.DefectItem

sealed class OfflineDefectDetailSideEffect: UiSideEffect() {
    data object NavigateToUp: OfflineDefectDetailSideEffect()
    data class NavigateToDefectEdit(val defectItem: DefectItem): OfflineDefectDetailSideEffect()
}