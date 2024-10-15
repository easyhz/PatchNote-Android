package com.easyhz.patchnote.ui.screen.filter.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect

sealed class FilterSideEffect: UiSideEffect() {
    data class NavigateToHome(val searchFieldParam: LinkedHashMap<String, String>): FilterSideEffect()
    data object ClearFocus: FilterSideEffect()
    data object NavigateToUp: FilterSideEffect()
}