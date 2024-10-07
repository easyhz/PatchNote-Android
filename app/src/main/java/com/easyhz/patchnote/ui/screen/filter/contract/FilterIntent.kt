package com.easyhz.patchnote.ui.screen.filter.contract

import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.core.model.filter.Filter
import com.easyhz.patchnote.core.model.filter.FilterValue

sealed class FilterIntent: UiIntent() {
    data class ChangeFilterValue(val filter: Filter, val value: FilterValue): FilterIntent()
}