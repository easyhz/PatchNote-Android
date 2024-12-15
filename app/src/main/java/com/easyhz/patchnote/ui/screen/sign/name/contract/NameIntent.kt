package com.easyhz.patchnote.ui.screen.sign.name.contract

import com.easyhz.patchnote.core.common.base.UiIntent

sealed class NameIntent: UiIntent() {
    data class ChangeNameText(val text: String): NameIntent()
    data object NavigateToUp: NameIntent()
    data object NavigateToTeam: NameIntent()
}