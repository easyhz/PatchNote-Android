package com.easyhz.patchnote.ui.screen.defectDetail.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect

sealed class DetailSideEffect: UiSideEffect() {
    data object NavigateToUp: DetailSideEffect()
}