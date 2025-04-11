package com.easyhz.patchnote.ui.screen.defect.defectDetail.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.defect.DefectMainItem
import com.easyhz.patchnote.core.model.defect.DefectProgress

sealed class DetailSideEffect: UiSideEffect() {
    data object NavigateToUp: DetailSideEffect()
    data class NavigateToDefectCompletion(val defectMainItem: DefectMainItem): DetailSideEffect()
    data class NavigateToDefectEdit(val defectItem: DefectItem): DetailSideEffect()
    data class NavigateToImageDetail(val defectItem: DefectItem, val selectedTab: DefectProgress, val selectedImage: Int): DetailSideEffect()
}