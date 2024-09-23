package com.easyhz.patchnote.ui.screen.dataEntry

import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.ui.screen.dataEntry.contract.DataEntryIntent
import com.easyhz.patchnote.ui.screen.dataEntry.contract.DataEntrySideEffect
import com.easyhz.patchnote.ui.screen.dataEntry.contract.DataEntryState
import com.easyhz.patchnote.ui.screen.dataEntry.contract.DataEntryState.Companion.updateDataEntryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DataEntryViewModel @Inject constructor(

): BaseViewModel<DataEntryState, DataEntryIntent, DataEntrySideEffect>(
    initialState = DataEntryState.init()
) {
    override fun handleIntent(intent: DataEntryIntent) {
        when(intent) {
            is DataEntryIntent.SelectDataEntryItemCategoryType -> { changeDataEntryItemCategoryType(intent.index, intent.categoryType) }
            is DataEntryIntent.ChangeDataEntryItemValue -> { changeDataEntryItemValue(intent.index, intent.value) }
            is DataEntryIntent.DeleteDataEntryItem -> { deleteDataEntryItem(intent.index) }
            is DataEntryIntent.NavigateToUp -> { navigateToUp() }
        }
    }

    private fun changeDataEntryItemCategoryType(index: Int, categoryType: CategoryType) {
        reduce { updateDataEntryItem(index = index, categoryType = categoryType) }
    }

    private fun changeDataEntryItemValue(index: Int, value: String) {
        reduce { updateDataEntryItem(index = index, value = value) }
    }

    private fun deleteDataEntryItem(index: Int) {
        reduce { copy(dataEntryList = dataEntryList.filterIndexed { i, _ -> i != index }.toMutableList()) }
    }

    private fun navigateToUp() {
        postSideEffect { DataEntrySideEffect.NavigateToUp }
    }
}