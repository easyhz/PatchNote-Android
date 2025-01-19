package com.easyhz.patchnote.ui.screen.setting.reception.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect

sealed class ReceptionSettingSideEffect: UiSideEffect() {
    data object NavigateToUp: ReceptionSettingSideEffect()
}