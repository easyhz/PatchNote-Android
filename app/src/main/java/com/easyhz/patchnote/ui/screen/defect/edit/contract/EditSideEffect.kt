package com.easyhz.patchnote.ui.screen.defect.edit.contract

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import com.easyhz.patchnote.core.common.base.UiSideEffect
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.defect.DefectItem

sealed class EditSideEffect: UiSideEffect() {
    data object ClearFocus: EditSideEffect()
    data object NavigateToGallery: EditSideEffect()
    data class NavigateToCamera(val uri: Uri): EditSideEffect()
    data object NavigateToUp: EditSideEffect()
    data class NavigateToDefectDetail(val defectItem: DefectItem): EditSideEffect()
    data class ShowSnackBar(val value: String): EditSideEffect()
    data class SendEntryItem(val entryItem: LinkedHashMap<CategoryType, TextFieldValue>): EditSideEffect()
}