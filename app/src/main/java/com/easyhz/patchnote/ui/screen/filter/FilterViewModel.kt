package com.easyhz.patchnote.ui.screen.filter

import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.util.DateFormatUtil
import com.easyhz.patchnote.core.common.util.toLinkedHashMap
import com.easyhz.patchnote.core.model.filter.Filter
import com.easyhz.patchnote.core.model.filter.FilterParam
import com.easyhz.patchnote.core.model.filter.FilterProgress
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

) : BaseViewModel<FilterState, FilterIntent, FilterSideEffect>(
    initialState = FilterState.init()
) {
    override fun handleIntent(intent: FilterIntent) {
        when (intent) {
            is FilterIntent.InitFilter -> {
                initFilter(intent.filterParam)
            }
            is FilterIntent.ChangeFilterValue -> {
                changeFilterValue(intent.filter, intent.value)
            }

            is FilterIntent.Search -> {
                searchDefect(intent.item)
            }

            is FilterIntent.NavigateToUp -> {
                navigateToUp()
            }

            is FilterIntent.ClearFilterValue -> {
                clearFilterValue(intent.filter)
            }
            is FilterIntent.Reset -> {
                reduce { FilterState.init() }
            }
        }
    }

    private fun initFilter(filterParam: FilterParam) {
        reduce { copy(filterItem = Filter.associateFilterValue(filterParam)) }
    }

    private fun changeFilterValue(filter: Filter, value: FilterValue) {
        reduce { updateFilterItemValue(filter, value) }
    }

    private fun searchDefect(item: LinkedHashMap<String, String>) {
        currentState.filterItem[Filter.REQUESTER]?.asString().takeIf { !it.isNullOrBlank() }?.let {
            item[Filter.REQUESTER.alias] = it
        }
        val indexFieldParam = currentState.filterItem.entries
            .filter { !it.key.isInSearchField }
            .filter {
                it.value.asLong() != null || it.value.asInt() != 0 || it.value.asString().isNotEmpty()
            }
            .associate { entry ->
                val value = when {
                    entry.value.asInt() != 0 -> FilterProgress.entries[entry.value.asInt()].progress.toString()
                    entry.value.asLong() != null -> DateFormatUtil.convertMillisToDate(entry.value.asLong()!!)
                    else -> entry.value.asString()
                }
                entry.key.alias to value
            }
            .toLinkedHashMap()

        val filterParam = FilterParam(
            searchFieldParam = item,
            indexFieldParam = indexFieldParam,
        )
        clearFocus()
        postSideEffect { FilterSideEffect.NavigateToHome(filterParam = filterParam) }
    }

    private fun clearFocus() {
        postSideEffect { FilterSideEffect.ClearFocus }
    }

    private fun navigateToUp() {
        postSideEffect { FilterSideEffect.NavigateToUp }
    }

    private fun clearFilterValue(filter: Filter) {
        reduce { updateFilterItemValue(filter, filter.createEmptyValue()) }
    }


}