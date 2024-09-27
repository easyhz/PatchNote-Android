package com.easyhz.patchnote.ui.screen.defectEntry.contract

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.category.Category
import com.easyhz.patchnote.core.model.category.CategoryType

data class DefectEntryState(
    val entryItem: LinkedHashMap<CategoryType, TextFieldValue>,
    val entryContent: String,
    val searchCategory: Map<CategoryType, List<String>>,
    val category: List<Category>,
) : UiState() {
    companion object {
        fun init() = DefectEntryState(
            entryItem = CategoryType.toLinkedHashMapTextFieldValue(),
            entryContent = "",
            searchCategory = CategoryType.toMapListString(),
            category = emptyList()
        )

        fun DefectEntryState.updateEntryItemValue(
            categoryType: CategoryType,
            value: TextFieldValue,
            isSelected: Boolean = false
        ): DefectEntryState {
            val updatedEntryItem = LinkedHashMap(entryItem)
            updatedEntryItem[categoryType] =
                if (isSelected) value.copy(selection = TextRange(value.text.length)
                ) else value
            return copy(entryItem = updatedEntryItem)
        }

        fun DefectEntryState.updateSearchCategory(
            categoryType: CategoryType,
            searchResult: List<String>
        ): DefectEntryState {
            val updatedSearchCategory = searchCategory.toMutableMap()
            updatedSearchCategory[categoryType] = searchResult
            return copy(searchCategory = updatedSearchCategory)
        }
    }
}
