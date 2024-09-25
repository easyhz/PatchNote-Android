package com.easyhz.patchnote.ui.screen.defectEntry

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.util.search.SearchHelper
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.category.getValue
import com.easyhz.patchnote.domain.usecase.category.FetchCategoryUseCase
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntryIntent
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntrySideEffect
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntryState
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntryState.Companion.updateEntryItemValue
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntryState.Companion.updateSearchCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DefectEntryViewModel @Inject constructor(
    private val fetchCategoryUseCase: FetchCategoryUseCase,
): BaseViewModel<DefectEntryState, DefectEntryIntent, DefectEntrySideEffect>(
    initialState = DefectEntryState.init()
) {
    override fun handleIntent(intent: DefectEntryIntent) {
        when(intent) {
            is DefectEntryIntent.ChangeEntryValueTextValue -> { onChangeEntryValueTextValue(intent.categoryType, intent.value) }
            is DefectEntryIntent.ClickCategoryDropDown -> { onClickCategoryDropDown(intent.categoryType, intent.value) }
        }
    }

    init {
        fetchCategory()
    }

    private fun fetchCategory() = viewModelScope.launch {
        fetchCategoryUseCase.invoke(Unit).onSuccess {
            reduce { copy(category = it) }
            currentState.category.forEach {
                searchCategory(it.type, "")
            }
        }.onFailure {
            println(">> 실패 : $it")
        }
    }

    private fun onChangeEntryValueTextValue(categoryType: CategoryType, value: TextFieldValue) {
        val filterValue = when(categoryType) {
            CategoryType.BUILDING, CategoryType.UNIT -> TextFieldValue(value.text.filter { it.isDigit() })
            else -> value
        }
        reduce { updateEntryItemValue(categoryType, filterValue) }
        searchCategory(categoryType, value.text)
    }

    private fun onClickCategoryDropDown(categoryType: CategoryType, value: String) {
        reduce { updateEntryItemValue(categoryType, TextFieldValue(value), isSelected = true) }
    }

    private fun searchCategory(categoryType: CategoryType, value: String) {
        val items = currentState.category.getValue(categoryType)?.values ?: emptyList()
        val searchResult = SearchHelper.search(value, items)
        reduce { updateSearchCategory(categoryType, searchResult) }
    }
}