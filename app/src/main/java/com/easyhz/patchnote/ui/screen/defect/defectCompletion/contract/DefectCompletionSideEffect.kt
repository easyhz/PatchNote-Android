package com.easyhz.patchnote.ui.screen.defect.defectCompletion.contract

import android.net.Uri
import com.easyhz.patchnote.core.common.base.UiSideEffect
import com.easyhz.patchnote.core.model.defect.DefectItem

sealed class DefectCompletionSideEffect: UiSideEffect() {
    data object ClearFocus: DefectCompletionSideEffect()
    data object NavigateToGallery: DefectCompletionSideEffect()
    data class NavigateToCamera(val uri: Uri): DefectCompletionSideEffect()
    data object NavigateToUp: DefectCompletionSideEffect()
    data class NavigateToDefectDetail(val defectItem: DefectItem): DefectCompletionSideEffect()
}