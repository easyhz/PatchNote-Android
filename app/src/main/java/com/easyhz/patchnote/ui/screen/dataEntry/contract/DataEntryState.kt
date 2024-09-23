package com.easyhz.patchnote.ui.screen.dataEntry.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.dataEntry.DataEntryItem

data class DataEntryState(
    val dataEntryList: MutableList<DataEntryItem>,
): UiState() {
    companion object {
        fun init() = DataEntryState(
            dataEntryList = mutableListOf(DataEntryItem.init())
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
            if (value != null && dataEntryList.lastIndex == index) {
                updatedDataEntryList.add(DataEntryItem.init())
            }
            return copy(dataEntryList = updatedDataEntryList)
        }
    }
}
