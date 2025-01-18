package com.easyhz.patchnote.ui.screen.setting.main

import androidx.lifecycle.SavedStateHandle
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.model.setting.EtcSettingItem
import com.easyhz.patchnote.core.model.setting.MySettingItem
import com.easyhz.patchnote.core.model.setting.SettingItem
import com.easyhz.patchnote.core.model.setting.TeamSettingItem
import com.easyhz.patchnote.ui.screen.setting.main.contract.SettingIntent
import com.easyhz.patchnote.ui.screen.setting.main.contract.SettingSideEffect
import com.easyhz.patchnote.ui.screen.setting.main.contract.SettingState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): BaseViewModel<SettingState, SettingIntent, SettingSideEffect>(
    initialState = SettingState.init()
) {
    override fun handleIntent(intent: SettingIntent) {
        when(intent) {
            is SettingIntent.ClickSettingItem -> onClickSettingItem(intent.settingItem)
            is SettingIntent.NavigateToUp -> navigateToUp()
            is SettingIntent.ChangeBlockText -> changeBlockInputDialogText(intent.text)
            is SettingIntent.ProposeBlock -> showSuccessDialog()
            is SettingIntent.HideBlockDialog -> hideInputDialog()
            is SettingIntent.HideSuccessDialog -> hideSuccessDialog()
        }
    }

    private fun onClickSettingItem(settingItem: SettingItem) {
        when(settingItem) {
            is TeamSettingItem -> handleTeamSettingItem(settingItem)
            is MySettingItem -> handleMySettingItem(settingItem)
            is EtcSettingItem -> handleEtcSettingItem(settingItem)
        }
    }

    private fun handleTeamSettingItem(settingItem: TeamSettingItem) {
        when(settingItem) {
            TeamSettingItem.DATA_MANAGEMENT -> navigateToDataManagement()
        }
    }

    private fun handleMySettingItem(settingItem: MySettingItem) {
        when(settingItem) {
            MySettingItem.MY_PAGE -> navigateToMyPage()
            MySettingItem.RECEPTION_SETTINGS -> println("RECEPTION_SETTINGS")
        }
    }

    private fun handleEtcSettingItem(settingItem: EtcSettingItem) {
        when(settingItem) {
            EtcSettingItem.ABOUT -> navigateToAbout()
            EtcSettingItem.BLOCK -> showInputDialog()
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

    private fun showInputDialog() {
        reduce { copy(isInputDialogVisible = true) }
    }

    private fun showSuccessDialog() {
        reduce { copy(isInputDialogVisible = false, isSuccessDialogVisible = true) }
    }

    private fun changeBlockInputDialogText(text: String) {
        reduce { copy(blockInputDialogText = text) }
    }

    private fun hideInputDialog() {
        reduce { copy(isInputDialogVisible = false) }
    }

    private fun hideSuccessDialog() {
        reduce { copy(isSuccessDialogVisible = false) }
    }

}