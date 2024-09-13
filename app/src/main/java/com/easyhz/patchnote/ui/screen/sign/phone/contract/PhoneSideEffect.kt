package com.easyhz.patchnote.ui.screen.sign.phone.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect

sealed class PhoneSideEffect: UiSideEffect() {
    data object NavigateToUp: PhoneSideEffect()
}