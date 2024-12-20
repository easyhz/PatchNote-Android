package com.easyhz.patchnote.ui.screen.setting.main.contract

import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.core.model.setting.SettingItem

sealed class SettingIntent: UiIntent() {
    data class ClickSettingItem(val settingItem: SettingItem): SettingIntent()
    data object NavigateToUp: SettingIntent()
    data class ChangeBlockText(val text: String): SettingIntent()
    data object ProposeBlock: SettingIntent()
    data object HideBlockDialog: SettingIntent()
    data object HideSuccessDialog: SettingIntent()
}