package com.easyhz.patchnote.ui.screen.home

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.AppError
import com.easyhz.patchnote.core.common.util.CrashlyticsLogger
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.filter.FilterParam
import com.easyhz.patchnote.domain.usecase.configuration.UpdateEnteredPasswordUseCase
import com.easyhz.patchnote.domain.usecase.defect.defect.GetDefectsPagingSourceUseCase
import com.easyhz.patchnote.domain.usecase.team.GetTeamNameUseCase
import com.easyhz.patchnote.domain.usecase.user.IsFirstOpenUseCase
import com.easyhz.patchnote.domain.usecase.user.SetIsFirstOpenUseCase
import com.easyhz.patchnote.ui.screen.home.contract.HomeIntent
import com.easyhz.patchnote.ui.screen.home.contract.HomeSideEffect
import com.easyhz.patchnote.ui.screen.home.contract.HomeState
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.FirebaseFirestoreException.Code
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val crashlyticsLogger: CrashlyticsLogger,
    private val isFirstOpenUseCase: IsFirstOpenUseCase,
    private val setIsFirstOpenUseCase: SetIsFirstOpenUseCase,
    private val getDefectsPagingSourceUseCase: GetDefectsPagingSourceUseCase,
    private val updateEnteredPasswordUseCase: UpdateEnteredPasswordUseCase,
    private val getTeamNameUseCase: GetTeamNameUseCase,
) : BaseViewModel<HomeState, HomeIntent, HomeSideEffect>(
    initialState = HomeState.init()
) {
    private val _defectState: MutableStateFlow<PagingData<DefectItem>> =
        MutableStateFlow(value = PagingData.empty())
    val defectState: MutableStateFlow<PagingData<DefectItem>>
        get() = _defectState
    private val tag = "HomeViewModel"

    override fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.FetchData -> fetchDefects(intent.filterParam)
            is HomeIntent.ClickSetting -> onClickSetting()
            is HomeIntent.ClickExport -> onClickExport()
            is HomeIntent.NavigateToDefectEntry -> navigateToDefectEntry()
            is HomeIntent.NavigateToFilter -> navigateToFilter()
            is HomeIntent.NavigateToDefectDetail -> navigateToDefectDetail(intent.defectItem)
            is HomeIntent.Refresh -> refresh(intent.filterParam)
            is HomeIntent.SetLoading -> reduce { copy(isLoading = intent.value) }
            is HomeIntent.HideOnboardingDialog -> hideOnboardingDialog()
            is HomeIntent.ShowOnboardingDialog -> setOnboardingDialog(true)
        }
    }

    init {
        getDefects()
        fetchIsFirstOpen()
        getTeamName()
    }

    private fun getDefects() {
        val jsonString = savedStateHandle.get<String?>("filterParam") ?: FilterParam().toString()
        val filterParam = Json.decodeFromString<FilterParam>(jsonString)

        fetchDefects(filterParam)
    }

    /* fetchDefects */
    private fun fetchDefects(filterParam: FilterParam) {
        viewModelScope.launch {
            getDefectsPagingSourceUseCase
                .invoke(filterParam = filterParam)
                .onEach {
                    if (currentState.isRefreshing) {
                        reduce { copy(isRefreshing = false) }
                    }
                }
                .catch { e ->
                    handleIndexError(e, filterParam)
                    Log.e(tag, "fetchDefects : $e", e)
                }.cachedIn(viewModelScope)
                .collect {
                    _defectState.value = it
                }
        }
    }

    private fun getTeamName() = viewModelScope.launch {
        getTeamNameUseCase.invoke()
            .distinctUntilChanged()
            .collect {
                reduce { copy(teamName = it) }
            }
    }

    /* fetchIsFirstOpen */
    private fun fetchIsFirstOpen() = viewModelScope.launch {
        isFirstOpenUseCase.invoke(Unit).onSuccess {
            if (!it) return@launch
            reduce { copy(isShowOnboardingDialog = true) }
        }.onFailure {
            Log.e(tag, "fetchIsFirstOpen : $it")
        }
    }

    private fun setIsFirstOpen() = viewModelScope.launch {
        setIsFirstOpenUseCase.invoke(false).onFailure {
            Log.e(tag, "setIsFirstOpen : $it")
        }
    }

    /* 세팅 화면 이동 */
    private fun navigateToSetting() {
        postSideEffect { HomeSideEffect.NavigateToSetting }
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


    /* updateEnteredPassword */
    private fun updateEnteredPassword() = viewModelScope.launch {
        updateEnteredPasswordUseCase.invoke(true).onFailure {
            Log.e(tag, "updateEnteredPassword : $it")
        }
    }

    private fun navigateToLogin() {
        postSideEffect { HomeSideEffect.NavigateToLogin }
    }

    private fun hideOnboardingDialog() {
        setIsFirstOpen()
        setOnboardingDialog(false)
    }

    private fun setOnboardingDialog(value: Boolean) {
        reduce { copy(isShowOnboardingDialog = value) }
    }

    private fun handleIndexError(e: Throwable, filterParam: FilterParam) {
        if (e is AppError.NoUserDataError) {
            navigateToLogin()
        }
        if (e is FirebaseFirestoreException && e.code == Code.FAILED_PRECONDITION) {
            val errorMap = mapOf(
                "FILTER_PARAM" to filterParam.toString(),
                "ERROR_MESSAGE" to e.message.toString()
            )
            crashlyticsLogger.setKey("INDEX_ERROR", errorMap.toString())
        } else {
            crashlyticsLogger.setKey("FETCH_ERROR", e.printStackTrace().toString())
        }
    }
}