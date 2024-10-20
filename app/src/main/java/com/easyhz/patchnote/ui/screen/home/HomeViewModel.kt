package com.easyhz.patchnote.ui.screen.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.BuildConfig
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.model.filter.FilterParam
import com.easyhz.patchnote.domain.usecase.configuration.FetchConfigurationUseCase
import com.easyhz.patchnote.domain.usecase.defect.FetchDefectsUseCase
import com.easyhz.patchnote.ui.screen.home.contract.HomeIntent
import com.easyhz.patchnote.ui.screen.home.contract.HomeSideEffect
import com.easyhz.patchnote.ui.screen.home.contract.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchConfigurationUseCase: FetchConfigurationUseCase,
    private val fetchDefectsUseCase: FetchDefectsUseCase,
): BaseViewModel<HomeState, HomeIntent, HomeSideEffect>(
    initialState = HomeState.init()
) {
    private val tag = "HomeViewModel"

    override fun handleIntent(intent: HomeIntent) {
        when(intent) {
            is HomeIntent.FetchData -> fetchDefects(intent.filterParam)
            is HomeIntent.NavigateToDataManagement -> navigateToDataManagement()
            is HomeIntent.NavigateToDefectEntry -> navigateToDefectEntry()
            is HomeIntent.NavigateToFilter -> navigateToFilter()
            is HomeIntent.NavigateToDefectDetail -> navigateToDefectDetail(intent.defectId)
            is HomeIntent.Refresh -> refresh(intent.filterParam)
            is HomeIntent.UpdateAppVersion -> updateAppVersion()
            is HomeIntent.NavigateToNotion -> updateAppVersion()
        }
    }

    init {
        fetchConfiguration()
    }


    /* fetchDefects */
    private fun fetchDefects(filterParam: FilterParam) = viewModelScope.launch {
        fetchDefectsUseCase.invoke(filterParam).onSuccess {
            reduce { copy(defectList = it) }
        }.onFailure {
            Log.e(tag, "fetchCategory : $it")
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
        }.onFailure {
            Log.e(tag, "fetchConfiguration : $it")
        }
    }

    /* 데이터 관리 화면 이동 */
    private fun navigateToDataManagement() {
        postSideEffect { HomeSideEffect.NavigateToDataManagement }
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
    private fun navigateToDefectDetail(defectId: String) {
        postSideEffect { HomeSideEffect.NavigateToDefectDetail(defectId = defectId) }
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
}