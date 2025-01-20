package com.easyhz.patchnote.ui.screen.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.BuildConfig
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.util.CrashlyticsLogger
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.filter.FilterParam
import com.easyhz.patchnote.domain.usecase.configuration.FetchConfigurationUseCase
import com.easyhz.patchnote.domain.usecase.configuration.UpdateEnteredPasswordUseCase
import com.easyhz.patchnote.domain.usecase.configuration.ValidatePasswordUseCase
import com.easyhz.patchnote.domain.usecase.defect.FetchDefectsUseCase
import com.easyhz.patchnote.ui.screen.home.contract.HomeIntent
import com.easyhz.patchnote.ui.screen.home.contract.HomeSideEffect
import com.easyhz.patchnote.ui.screen.home.contract.HomeState
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.FirebaseFirestoreException.Code
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val crashlyticsLogger: CrashlyticsLogger,
    private val fetchConfigurationUseCase: FetchConfigurationUseCase,
    private val fetchDefectsUseCase: FetchDefectsUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val updateEnteredPasswordUseCase: UpdateEnteredPasswordUseCase,
): BaseViewModel<HomeState, HomeIntent, HomeSideEffect>(
    initialState = HomeState.init()
) {
    private val tag = "HomeViewModel"

    override fun handleIntent(intent: HomeIntent) {
        when(intent) {
            is HomeIntent.FetchData -> fetchDefects(intent.filterParam)
            is HomeIntent.ClickSetting -> onClickSetting()
            is HomeIntent.ClickExport -> onClickExport()
            is HomeIntent.NavigateToDefectEntry -> navigateToDefectEntry()
            is HomeIntent.NavigateToFilter -> navigateToFilter()
            is HomeIntent.NavigateToDefectDetail -> navigateToDefectDetail(intent.defectItem)
            is HomeIntent.Refresh -> refresh(intent.filterParam)
            is HomeIntent.UpdateAppVersion -> updateAppVersion()
            is HomeIntent.NavigateToNotion -> updateAppVersion()
            is HomeIntent.ChangePasswordText -> changePasswordText(intent.newValue)
            is HomeIntent.CheckPassword -> checkPassword()
            is HomeIntent.HidePasswordDialog -> hidePasswordDialog()
            is HomeIntent.HidePasswordErrorDialog -> setPasswordErrorDialog(false)
            is HomeIntent.SetLoading -> reduce { copy(isLoading = intent.value) }
        }
    }

    init {
        fetchConfiguration()
    }


    /* fetchDefects */
    private fun fetchDefects(filterParam: FilterParam) = viewModelScope.launch {
        fetchDefectsUseCase.invoke(filterParam).onSuccess {
            reduce { copy(defectList = it) }
        }.onFailure { e ->
            handleIndexError(e, filterParam)
            Log.e(tag, "fetchDefects : $e", e)
        }.also {
            if (currentState.isRefreshing) {
                reduce { copy(isRefreshing = false) }
            }
        }
    }

    /* fetchConfiguration */
    private fun fetchConfiguration() = viewModelScope.launch {
        fetchConfigurationUseCase.invoke(Unit).onSuccess {
            val isLatestVersion = it.androidVersion == BuildConfig.VERSION_NAME
            reduce { copy(isLatestVersion = isLatestVersion, appConfiguration = it) }
            validatePassword(it.settingPassword)
        }.onFailure {
            Log.e(tag, "fetchConfiguration : $it")
        }
    }

    private fun validatePassword(password: String) = viewModelScope.launch {
        validatePasswordUseCase.invoke(password).onSuccess {
            reduce { copy(hasPassword = it) }
        }.onFailure {
            Log.e(tag, "validatePassword : $it")
        }
    }

    /* 세팅 화면 이동 */
    private fun navigateToSetting() {
        postSideEffect { HomeSideEffect.NavigateToSetting(currentState.appConfiguration.notionUrl) }
    }

    /* 하자 등록 화면 이동 */
    private fun navigateToDefectEntry() {
        postSideEffect { HomeSideEffect.NavigateToDefectEntry }
    }

    /* 필터 화면 이동 */
    private fun navigateToFilter() {
        postSideEffect { HomeSideEffect.NavigateToFilter }
    }

    /* 하자 상세 화면 이동 */
    private fun navigateToDefectDetail(defectItem: DefectItem) {
        postSideEffect { HomeSideEffect.NavigateToDefectDetail(defectItem = defectItem) }
    }

    /* onClickDataManagement */
    private fun onClickSetting() {
        navigateToSetting()
    }

    private fun onClickExport() {
        postSideEffect { HomeSideEffect.NavigateToExport }
    }

    /* refresh */
    private fun refresh(filterParam: FilterParam) = viewModelScope.launch {
        reduce { copy(isRefreshing = true) }
        fetchDefects(filterParam)
    }

    /* updateAppVersion */
    private fun updateAppVersion() {
        postSideEffect { HomeSideEffect.NavigateToVersionUpdate(currentState.appConfiguration.notionUrl) }
    }

    /* changePasswordText */
    private fun changePasswordText(newValue: String) {
        reduce { copy(password = newValue) }
    }

    /* hidePasswordDialog */
    private fun hidePasswordDialog() {
        reduce { copy(isShowPasswordDialog = false, password = "") }
    }

    /* focusRequest */
    private fun requestFocus() = viewModelScope.launch {
        delay(300)
        postSideEffect { HomeSideEffect.RequestFocus }
    }

    /* checkPassword */
    private fun checkPassword() = viewModelScope.launch {
        if (currentState.password == currentState.appConfiguration.settingPassword) {
            reduce { copy(isShowPasswordDialog = false, hasPassword = true, password = "") }
            updateEnteredPassword()
            navigateToSetting()
        } else {
            setPasswordErrorDialog(true)
        }
    }

    /* updateEnteredPassword */
    private fun updateEnteredPassword() = viewModelScope.launch {
        updateEnteredPasswordUseCase.invoke(true).onFailure {
            Log.e(tag, "updateEnteredPassword : $it")
        }
    }

    /* setPasswordErrorDialog */
    private fun setPasswordErrorDialog(value: Boolean) = viewModelScope.launch {
        reduce { copy(isShowPasswordErrorDialog = value) }
    }

    private fun handleIndexError(e: Throwable, filterParam: FilterParam) {
        if (e is FirebaseFirestoreException && e.code == Code.FAILED_PRECONDITION) {
            val errorMap = mapOf(
                "FILTER_PARAM" to filterParam.toString(),
                "ERROR_MESSAGE" to e.message.toString()
            )
            crashlyticsLogger.setKey("INDEX_ERROR", errorMap.toString())
        }
    }
}