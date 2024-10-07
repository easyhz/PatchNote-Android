package com.easyhz.patchnote.ui.screen.filter.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.filter.Filter
import com.easyhz.patchnote.core.model.filter.FilterValue

data class FilterState(
    val filterItem: LinkedHashMap<Filter, FilterValue>,
): UiState() {
    companion object {
        fun init() = FilterState(
            filterItem = Filter.toLinkedHashMap(),
        )

        fun FilterState.updateFilterItemValue(filter: Filter, value: FilterValue): FilterState {
            val updatedFilterItem = LinkedHashMap(filterItem)
            updatedFilterItem[filter] = value
            return copy(filterItem = updatedFilterItem)
        }
    }

}
