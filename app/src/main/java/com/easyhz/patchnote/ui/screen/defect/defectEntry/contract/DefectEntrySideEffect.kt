package com.easyhz.patchnote.ui.screen.defect.defectEntry.contract

import android.net.Uri
import com.easyhz.patchnote.core.common.base.UiSideEffect

sealed class DefectEntrySideEffect: UiSideEffect() {
    data object ClearFocus: DefectEntrySideEffect()
    data object ClearFocusDelayed: DefectEntrySideEffect() // 새로 추가
    data object NavigateToGallery: DefectEntrySideEffect()
    data class NavigateToCamera(val uri: Uri): DefectEntrySideEffect()
    data object NavigateToUp: DefectEntrySideEffect()
    data object NavigateToHome: DefectEntrySideEffect()
    data class ShowSnackBar(val value: String): DefectEntrySideEffect()
    data object SendClear: DefectEntrySideEffect()
}
