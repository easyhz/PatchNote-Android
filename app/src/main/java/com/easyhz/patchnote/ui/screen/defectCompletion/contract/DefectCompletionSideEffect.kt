package com.easyhz.patchnote.ui.screen.defectCompletion.contract

import android.net.Uri
import com.easyhz.patchnote.core.common.base.UiSideEffect

sealed class DefectCompletionSideEffect: UiSideEffect() {
    data object ClearFocus: DefectCompletionSideEffect()
    data object NavigateToGallery: DefectCompletionSideEffect()
    data class NavigateToCamera(val uri: Uri): DefectCompletionSideEffect()
    data object NavigateToUp: DefectCompletionSideEffect()
}