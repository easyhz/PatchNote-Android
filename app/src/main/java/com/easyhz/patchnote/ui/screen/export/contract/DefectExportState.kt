package com.easyhz.patchnote.ui.screen.export.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.filter.Filter
import com.easyhz.patchnote.core.model.filter.FilterValue
import com.easyhz.patchnote.core.model.filter.FilterValue.Companion.asLong

data class DefectExportState(
    val filterItem: LinkedHashMap<Filter, FilterValue>,
    val isLoading: Boolean,
    val isShowExportDialog: Boolean,
    val isShowDateDialog: Boolean,
): UiState() {
    companion object {
        fun init() = DefectExportState(
            filterItem = Filter.toLinkedHashMap(),
            isLoading = false,
            isShowExportDialog = false,
            isShowDateDialog = false
        )

        fun DefectExportState.updateFilterItemValue(filter: Filter, value: FilterValue): DefectExportState {
            val updatedFilterItem = LinkedHashMap(filterItem)
            updatedFilterItem[filter] = value
            return copy(filterItem = updatedFilterItem)
        }

    }
    fun isDateAllEmpty(): Boolean {
        return filterItem[Filter.REQUEST_DATE]?.asLong() == null && filterItem[Filter.COMPLETION_DATE]?.asLong() == null
    }
}
