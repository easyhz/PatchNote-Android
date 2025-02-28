package com.easyhz.patchnote.ui.screen.defect.edit.contract

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import com.easyhz.patchnote.core.common.base.UiSideEffect
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.defect.DefectItem

sealed class DefectEditSideEffect: UiSideEffect() {
    data object ClearFocus: DefectEditSideEffect()
    data object NavigateToGallery: DefectEditSideEffect()
    data class NavigateToCamera(val uri: Uri): DefectEditSideEffect()
    data object NavigateToUp: DefectEditSideEffect()
    data class NavigateToDefectDetail(val defectItem: DefectItem): DefectEditSideEffect()
    data class ShowSnackBar(val value: String): DefectEditSideEffect()
    data class SendEntryItem(val entryItem: LinkedHashMap<CategoryType, TextFieldValue>): DefectEditSideEffect()
}