package com.easyhz.patchnote.ui.screen.filter.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect

sealed class FilterSideEffect: UiSideEffect() {
    data class NavigateToHome(val param: List<String>): FilterSideEffect()
    data object ClearFocus: FilterSideEffect()
    data object NavigateToUp: FilterSideEffect()
}