package com.easyhz.patchnote.ui.screen.filter

import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.model.filter.Filter
import com.easyhz.patchnote.core.model.filter.FilterValue
import com.easyhz.patchnote.core.model.filter.FilterValue.Companion.asInt
import com.easyhz.patchnote.core.model.filter.FilterValue.Companion.asLong
import com.easyhz.patchnote.core.model.filter.FilterValue.Companion.asString
import com.easyhz.patchnote.ui.screen.filter.contract.FilterIntent
import com.easyhz.patchnote.ui.screen.filter.contract.FilterSideEffect
import com.easyhz.patchnote.ui.screen.filter.contract.FilterState
import com.easyhz.patchnote.ui.screen.filter.contract.FilterState.Companion.updateFilterItemValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(

): BaseViewModel<FilterState, FilterIntent, FilterSideEffect>(
    initialState = FilterState.init()
) {
    override fun handleIntent(intent: FilterIntent) {
        when(intent) {
            is FilterIntent.ChangeFilterValue -> { changeFilterValue(intent.filter, intent.value) }
            is FilterIntent.Search -> { searchDefect(intent.item) }
        }
    }

    private fun changeFilterValue(filter: Filter, value: FilterValue) {
        reduce { updateFilterItemValue(filter, value) }
    }

    private fun searchDefect(item: List<String>) {
        val search = currentState.filterItem.entries.filter {
            it.value.asLong() != null || it.value.asInt() != 0 || it.value.asString() != ""
        }.map { "${it.key.alias}=${it.value.value}" }
        val param = search + item
        clearFocus()
        postSideEffect { FilterSideEffect.NavigateToHome(param) }
    }

    private fun clearFocus() {
        postSideEffect { FilterSideEffect.ClearFocus }
    }


}