package com.easyhz.patchnote.ui.screen.defect.common.contract

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.category.Category
import com.easyhz.patchnote.core.model.category.CategoryType

data class DefectState(
    val entryItem: LinkedHashMap<CategoryType, TextFieldValue>,
    val searchCategory: Map<CategoryType, List<String>>,
    val category: List<Category>
): UiState() {
    companion object {
        fun init() = DefectState(
            entryItem = CategoryType.toLinkedHashMapTextFieldValue(),
            searchCategory = CategoryType.toMapListString(),
            category = emptyList()
        )

        fun DefectState.updateEntryItemValue(
            categoryType: CategoryType,
            value: TextFieldValue,
            isSelected: Boolean = false
        ): DefectState {
            val updatedEntryItem = LinkedHashMap(entryItem)
            updatedEntryItem[categoryType] =
                if (isSelected) value.copy(selection = TextRange(value.text.length))
                else value
            return copy(entryItem = updatedEntryItem)
        }

        fun DefectState.updateSearchCategory(
            categoryType: CategoryType,
            searchResult: List<String>
        ): DefectState {
            val updatedSearchCategory = searchCategory.toMutableMap()
            updatedSearchCategory[categoryType] = searchResult
            return copy(searchCategory = updatedSearchCategory)
        }

        fun DefectState.clearEntryItemValue(categoryType: LinkedHashMap<CategoryType, Boolean>): DefectState {
            val updatedEntryItem = LinkedHashMap(entryItem)
            categoryType
                .filterValues { !it }
                .forEach { (key, _) ->
                    updatedEntryItem[key] = TextFieldValue("")
                }
            return copy(entryItem = updatedEntryItem)
        }

        fun DefectState.resetData(): DefectState {
            val updatedEntryItem = LinkedHashMap(entryItem)
            CategoryType.entries.forEach { item ->
                updatedEntryItem[item] = TextFieldValue("")
            }
            return copy(entryItem = updatedEntryItem)
        }
    }
}
