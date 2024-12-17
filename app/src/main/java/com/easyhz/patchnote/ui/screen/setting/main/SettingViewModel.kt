package com.easyhz.patchnote.ui.screen.setting.main

import androidx.lifecycle.SavedStateHandle
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.model.setting.SettingItem
import com.easyhz.patchnote.ui.screen.setting.main.contract.SettingIntent
import com.easyhz.patchnote.ui.screen.setting.main.contract.SettingSideEffect
import com.easyhz.patchnote.ui.screen.setting.main.contract.SettingState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): BaseViewModel<SettingState, SettingIntent, SettingSideEffect>(
    initialState = SettingState
) {
    override fun handleIntent(intent: SettingIntent) {
        when(intent) {
            is SettingIntent.ClickSettingItem -> onClickSettingItem(intent.settingItem)
            is SettingIntent.NavigateToUp -> navigateToUp()
        }
    }

    private fun onClickSettingItem(settingItem: SettingItem) {
        when(settingItem) {
            SettingItem.ABOUT -> navigateToAbout()
            SettingItem.DATA_MANAGEMENT -> navigateToDataManagement()
            SettingItem.MY_PAGE -> navigateToMyPage()
        }
    }

    private fun navigateToUp() {
        postSideEffect { SettingSideEffect.NavigateToUp }
    }

    private fun navigateToAbout() {
        val notionUrl: String? = savedStateHandle["notionUrl"]
        notionUrl ?: return
        postSideEffect { SettingSideEffect.NavigateToAbout(notionUrl) }
    }

    private fun navigateToDataManagement() {
        postSideEffect { SettingSideEffect.NavigateToDataManagement }
    }

    private fun navigateToMyPage() {
        postSideEffect { SettingSideEffect.NavigateToMyPage }
    }

}