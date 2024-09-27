package com.easyhz.patchnote.ui.screen.defectEntry.contract

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.category.Category
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.image.DefectImage

data class DefectEntryState(
    val entryItem: LinkedHashMap<CategoryType, TextFieldValue>,
    val entryContent: String,
    val images: List<DefectImage>,
    val searchCategory: Map<CategoryType, List<String>>,
    val category: List<Category>,
    val isShowImageBottomSheet: Boolean
) : UiState() {
    companion object {
        fun init() = DefectEntryState(
            entryItem = CategoryType.toLinkedHashMapTextFieldValue(),
            entryContent = "",
            images = emptyList(),
            searchCategory = CategoryType.toMapListString(),
            category = emptyList(),
            isShowImageBottomSheet = false
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

        fun DefectEntryState.updateImages(newImages: List<DefectImage>): DefectEntryState =
            this.copy(images = this.images + newImages)

        fun DefectEntryState.deleteImage(image: DefectImage): DefectEntryState =
            this.copy(images = this.images.filter { it.id != image.id })
    }
}
