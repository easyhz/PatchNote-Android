package com.easyhz.patchnote.ui.screen.setting.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.model.setting.EtcSettingItem
import com.easyhz.patchnote.core.model.setting.MySettingItem
import com.easyhz.patchnote.core.model.setting.SettingItem
import com.easyhz.patchnote.core.model.setting.TeamSettingItem
import com.easyhz.patchnote.domain.usecase.configuration.FetchConfigurationUseCase
import com.easyhz.patchnote.domain.usecase.team.GetTeamNameUseCase
import com.easyhz.patchnote.ui.screen.setting.main.contract.SettingIntent
import com.easyhz.patchnote.ui.screen.setting.main.contract.SettingSideEffect
import com.easyhz.patchnote.ui.screen.setting.main.contract.SettingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val fetchConfigurationUseCase: FetchConfigurationUseCase,
    private val getTeamNameUseCase: GetTeamNameUseCase,
) : BaseViewModel<SettingState, SettingIntent, SettingSideEffect>(
    initialState = SettingState.init()
) {
    private val tag = "SettingViewModel"
    override fun handleIntent(intent: SettingIntent) {
        when (intent) {
            is SettingIntent.ClickSettingItem -> onClickSettingItem(intent.settingItem)
            is SettingIntent.NavigateToUp -> navigateToUp()
            is SettingIntent.ChangeBlockText -> changeBlockInputDialogText(intent.text)
            is SettingIntent.ProposeBlock -> showSuccessDialog()
            is SettingIntent.HideBlockDialog -> hideInputDialog()
            is SettingIntent.HideSuccessDialog -> hideSuccessDialog()
        }
    }

    init {
        getTeamName()
    }

    private fun getTeamName() {
        viewModelScope.launch {
            getTeamNameUseCase.invoke()
                .distinctUntilChanged()
                .collect {
                    reduce { copy(teamName = it) }
                }
        }
    }

    private fun onClickSettingItem(settingItem: SettingItem) {
        when (settingItem) {
            is TeamSettingItem -> handleTeamSettingItem(settingItem)
            is MySettingItem -> handleMySettingItem(settingItem)
            is EtcSettingItem -> handleEtcSettingItem(settingItem)
        }
    }

    private fun handleTeamSettingItem(settingItem: TeamSettingItem) {
        when (settingItem) {
            TeamSettingItem.TEAM_INFORMATION -> navigateToTeamInformation()
            TeamSettingItem.DATA_MANAGEMENT -> navigateToDataManagement()
            TeamSettingItem.TEAM_LIST -> navigateToTeamSelection()
        }
    }

    private fun handleMySettingItem(settingItem: MySettingItem) {
        when (settingItem) {
            MySettingItem.MY_PAGE -> navigateToMyPage()
            MySettingItem.RECEPTION_SETTINGS -> navigateToReceptionSetting()
        }
    }

    private fun handleEtcSettingItem(settingItem: EtcSettingItem) {
        when (settingItem) {
            EtcSettingItem.ABOUT -> navigateToAbout()
            EtcSettingItem.SUPPORT -> navigateToSupport(settingItem.getValue())
            EtcSettingItem.BLOCK -> showInputDialog()
            EtcSettingItem.VERSION -> {}
        }
    }

    private fun navigateToUp() {
        postSideEffect { SettingSideEffect.NavigateToUp }
    }

    private fun navigateToAbout() = viewModelScope.launch {
        fetchConfigurationUseCase.invoke(Unit).onSuccess {
            postSideEffect { SettingSideEffect.NavigateToAbout(it.notionUrl) }
        }.onFailure {
            Log.e(tag, "fetchConfiguration : $it")
        }
    }

    private fun navigateToTeamInformation() {
        postSideEffect { SettingSideEffect.NavigateToTeamInformation }
    }

    private fun navigateToDataManagement() {
        postSideEffect { SettingSideEffect.NavigateToDataManagement }
    }

    private fun navigateToTeamSelection() {
        postSideEffect { SettingSideEffect.NavigateToTeamSelection }
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

    private fun navigateToReceptionSetting() {
        postSideEffect { SettingSideEffect.NavigateToReceptionSetting }
    }

    private fun navigateToSupport(url: String?) {
        if (url.isNullOrBlank()) return
        postSideEffect { SettingSideEffect.NavigateToSupport(url) }
    }

}