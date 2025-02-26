package com.easyhz.patchnote.ui.screen.defect.defectDetail.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect
import com.easyhz.patchnote.core.model.defect.DefectMainItem

sealed class DetailSideEffect: UiSideEffect() {
    data object NavigateToUp: DetailSideEffect()
    data class NavigateToDefectCompletion(val defectMainItem: DefectMainItem): DetailSideEffect()
}