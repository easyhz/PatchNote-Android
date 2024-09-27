package com.easyhz.patchnote.ui.screen.defectEntry.contract

import android.net.Uri
import com.easyhz.patchnote.core.common.base.UiSideEffect

sealed class DefectEntrySideEffect: UiSideEffect() {
    data object NavigateToGallery: DefectEntrySideEffect()
    data class NavigateToCamera(val uri: Uri): DefectEntrySideEffect()
}