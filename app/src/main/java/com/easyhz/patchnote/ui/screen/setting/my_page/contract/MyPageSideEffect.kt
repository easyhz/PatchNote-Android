package com.easyhz.patchnote.ui.screen.setting.my_page.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect

sealed class MyPageSideEffect: UiSideEffect() {
    data object NavigateToOnboarding: MyPageSideEffect()
    data object NavigateToUp: MyPageSideEffect()
    data class ShowSnackBar(val value: String): MyPageSideEffect()
}