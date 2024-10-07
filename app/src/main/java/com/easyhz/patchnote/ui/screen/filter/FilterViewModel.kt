package com.easyhz.patchnote.ui.screen.filter

import androidx.compose.runtime.Composable
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.model.filter.Filter
import com.easyhz.patchnote.core.model.filter.FilterValue
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
        }
    }

    private fun changeFilterValue(filter: Filter, value: FilterValue) {
        reduce { updateFilterItemValue(filter, value) }
    }
}