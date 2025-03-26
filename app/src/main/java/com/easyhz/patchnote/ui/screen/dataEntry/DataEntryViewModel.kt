package com.easyhz.patchnote.ui.screen.dataEntry

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.handleError
import com.easyhz.patchnote.core.common.util.resource.ResourceHelper
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.domain.usecase.category.UpdateCategoryUseCase
import com.easyhz.patchnote.ui.screen.dataEntry.contract.DataEntryIntent
import com.easyhz.patchnote.ui.screen.dataEntry.contract.DataEntrySideEffect
import com.easyhz.patchnote.ui.screen.dataEntry.contract.DataEntryState
import com.easyhz.patchnote.ui.screen.dataEntry.contract.DataEntryState.Companion.updateDataEntryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataEntryViewModel @Inject constructor(
    private val resourceHelper: ResourceHelper,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
): BaseViewModel<DataEntryState, DataEntryIntent, DataEntrySideEffect>(
    initialState = DataEntryState.init()
) {
    override fun handleIntent(intent: DataEntryIntent) {
        when(intent) {
            is DataEntryIntent.SelectDataEntryItemCategoryType -> { changeDataEntryItemCategoryType(intent.index, intent.categoryType) }
            is DataEntryIntent.ChangeDataEntryItemValue -> { changeDataEntryItemValue(intent.index, intent.value) }
            is DataEntryIntent.DeleteDataEntryItem -> { deleteDataEntryItem(intent.index) }
            is DataEntryIntent.UpdateDataEntryItem -> { updateCategory() }
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
        if (currentState.dataEntryList.size == 1) return
        reduce { copy(dataEntryList = dataEntryList.filterIndexed { i, _ -> i != index }.toMutableList()) }
    }

    private fun navigateToUp() {
        postSideEffect { DataEntrySideEffect.NavigateToUp }
    }

    private fun updateCategory() = viewModelScope.launch {
        setLoading(true)
        val param = currentState.dataEntryList.filter { it.value.isNotBlank() }
        if (param.isEmpty()) return@launch
        updateCategoryUseCase.invoke(param).onSuccess {
            hideKeyboard()
        }.onFailure { e ->
            Log.e(this.javaClass.name, "updateCategory : ${e.message}")
            showSnackBar(resourceHelper, e.handleError()) {
                DataEntrySideEffect.ShowSnackBar(it)
            }
        }.also { setLoading(false) }

    }

    private fun hideKeyboard() {
        postSideEffect { DataEntrySideEffect.HideKeyboard }
    }

    private fun setLoading(isLoading: Boolean) {
        reduce { copy(isLoading = isLoading) }
    }
}