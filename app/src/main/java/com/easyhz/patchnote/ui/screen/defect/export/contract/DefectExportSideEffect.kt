package com.easyhz.patchnote.ui.screen.defect.export.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect
import java.io.File

sealed class DefectExportSideEffect: UiSideEffect() {
    data object ClearFocus: DefectExportSideEffect()
    data object NavigateToUp: DefectExportSideEffect()data object RequestPermission: DefectExportSideEffect()
    data class ShareIntent(val file: File): DefectExportSideEffect()
    data class ShowSnackBar(val value: String): DefectExportSideEffect()
}