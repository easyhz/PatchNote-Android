package com.easyhz.patchnote.ui.screen.setting.main.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect

sealed class SettingSideEffect: UiSideEffect() {
    data object NavigateToUp: SettingSideEffect()
    data class NavigateToAbout(val url: String): SettingSideEffect()
    data object NavigateToTeamInformation: SettingSideEffect()
    data object NavigateToDataManagement: SettingSideEffect()
    data object NavigateToTeamSelection: SettingSideEffect()
    data object NavigateToMyPage: SettingSideEffect()
    data object NavigateToReceptionSetting: SettingSideEffect()
    data object NavigateToImageSetting: SettingSideEffect()
    data class NavigateToSupport(val url: String): SettingSideEffect()
}