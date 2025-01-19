package com.easyhz.patchnote.ui.screen.setting.reception.contract

import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.core.model.category.CategoryType

sealed class ReceptionSettingIntent: UiIntent() {
    data object NavigateToUp: ReceptionSettingIntent()
    data class ClickToggleButton(val categoryType: CategoryType, val newValue: Boolean): ReceptionSettingIntent()
}