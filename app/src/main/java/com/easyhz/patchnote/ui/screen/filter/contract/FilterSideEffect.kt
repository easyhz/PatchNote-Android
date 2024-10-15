package com.easyhz.patchnote.ui.screen.filter.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect
import com.easyhz.patchnote.core.model.filter.FilterParam

sealed class FilterSideEffect: UiSideEffect() {
    data class NavigateToHome(val filterParam: FilterParam): FilterSideEffect()
    data object ClearFocus: FilterSideEffect()
    data object NavigateToUp: FilterSideEffect()
}