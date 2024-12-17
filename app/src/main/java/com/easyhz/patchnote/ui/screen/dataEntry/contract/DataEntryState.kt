package com.easyhz.patchnote.ui.screen.dataEntry.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.dataEntry.DataEntryItem

data class DataEntryState(
    val dataEntryList: MutableList<DataEntryItem>,
    val isLoading: Boolean,
): UiState() {
    companion object {
        fun init() = DataEntryState(
            dataEntryList = mutableListOf(DataEntryItem.init()),
            isLoading = false
        )

        fun DataEntryState.updateDataEntryItem(
            index: Int,
            categoryType: CategoryType? = null,
            value: String? = null
        ): DataEntryState {
            val updatedDataEntryList = dataEntryList.toMutableList()
            updatedDataEntryList[index] = dataEntryList[index].copy(
                categoryType = categoryType ?: dataEntryList[index].categoryType,
                value = value ?: dataEntryList[index].value
            )
            if (!value.isNullOrBlank() && dataEntryList.lastIndex == index) {
                updatedDataEntryList.add(DataEntryItem.init())
            } else if (value.isNullOrBlank() && dataEntryList.lastIndex - 1 == index) {
                updatedDataEntryList.removeAt(dataEntryList.lastIndex)
            }
            return copy(dataEntryList = updatedDataEntryList)
        }
    }
}
