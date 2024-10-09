package com.easyhz.patchnote.ui.screen.defect.contract

import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.text.input.TextFieldValue
import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.core.model.category.CategoryType

sealed class DefectIntent: UiIntent() {
    data class ChangeEntryValueTextValue(val categoryType: CategoryType, val value: TextFieldValue): DefectIntent()
    data class ClickCategoryDropDown(val categoryType: CategoryType, val value: String): DefectIntent()
    data class ChangeFocusState(val categoryType: CategoryType, val focusState: FocusState): DefectIntent()
    data object ValidateEntryItem: DefectIntent()
    data object ClearData: DefectIntent()
    data object SearchItem: DefectIntent()
}