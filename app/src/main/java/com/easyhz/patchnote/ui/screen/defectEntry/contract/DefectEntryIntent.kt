package com.easyhz.patchnote.ui.screen.defectEntry.contract

import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.text.input.TextFieldValue
import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.core.model.category.CategoryType

sealed class DefectEntryIntent: UiIntent() {
    data class ChangeEntryValueTextValue(val categoryType: CategoryType, val value: TextFieldValue): DefectEntryIntent()
    data class ClickCategoryDropDown(val categoryType: CategoryType, val value: String): DefectEntryIntent()
    data class ChangeFocusState(val categoryType: CategoryType, val focusState: FocusState): DefectEntryIntent()
}