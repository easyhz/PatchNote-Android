package com.easyhz.patchnote.ui.screen.defect.offline.defect

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.util.log.Logger
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.domain.usecase.configuration.FetchConfigurationUseCase
import com.easyhz.patchnote.domain.usecase.defect.GetOfflineDefectsPagingSourceUseCase
import com.easyhz.patchnote.domain.usecase.defect.UploadAllOfflineDefectToRemoteUseCase
import com.easyhz.patchnote.domain.usecase.user.IsOfflineFirstOpenUseCase
import com.easyhz.patchnote.domain.usecase.user.SetIsOfflineFirstOpenUseCase
import com.easyhz.patchnote.ui.screen.defect.offline.defect.contract.OfflineDefectIntent
import com.easyhz.patchnote.ui.screen.defect.offline.defect.contract.OfflineDefectSideEffect
import com.easyhz.patchnote.ui.screen.defect.offline.defect.contract.OfflineDefectState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfflineDefectViewModel @Inject constructor(
    private val logger: Logger,
    private val getOfflineDefectsPagingSourceUseCase: GetOfflineDefectsPagingSourceUseCase,
    private val fetchConfigurationUseCase: FetchConfigurationUseCase,
    private val uploadAllOfflineDefectToRemoteUseCase: UploadAllOfflineDefectToRemoteUseCase,
    private val isOfflineFirstOpenUseCase: IsOfflineFirstOpenUseCase,
    private val setIsOfflineFirstOpenUseCase: SetIsOfflineFirstOpenUseCase,
): BaseViewModel<OfflineDefectState, OfflineDefectIntent, OfflineDefectSideEffect>(
    initialState = OfflineDefectState.init()
) {
    private val tag = "OfflineDefectViewModel"
    private val _defectState: MutableStateFlow<PagingData<DefectItem>> =
        MutableStateFlow(value = PagingData.empty())
    val defectState: MutableStateFlow<PagingData<DefectItem>>
        get() = _defectState

    override fun handleIntent(intent: OfflineDefectIntent) {
        when(intent) {
            is OfflineDefectIntent.Refresh -> refresh()
            is OfflineDefectIntent.ClickSetting -> onClickSetting()
            is OfflineDefectIntent.ClickAllUpload -> onClickUpload()
            is OfflineDefectIntent.NavigateToOfflineDefectDetail -> navigateToOfflineDefectDetail(intent.defectItem)
            is OfflineDefectIntent.HideOnboardingDialog -> setOnboardingDialog(false)
            is OfflineDefectIntent.ClickTopBarName -> setOnboardingDialog(true)
            is OfflineDefectIntent.UploadAllOfflineDefect -> uploadAllOfflineDefect()
            is OfflineDefectIntent.HideUploadDialog -> setUploadDialog(false)
            is OfflineDefectIntent.HideUploadSuccessDialog -> reduce { copy(isShowUploadSuccessDialog = false) }
        }
    }

    init {
        fetchOfflineDefects()
        fetchConfiguration()
        fetchIsOfflineFirstOpen()
    }

    /* fetchOfflineDefects */
    private fun fetchOfflineDefects() {
        viewModelScope.launch {
            getOfflineDefectsPagingSourceUseCase.invoke()
                .onEach {
                    if (currentState.isRefreshing) {
                        reduce { copy(isRefreshing = false) }
                    }
                }
                .catch { e ->
                    logger.e(tag, "fetchDefects : $e", e)
                }.cachedIn(viewModelScope)
                .collect {
                    _defectState.value = it
                }
        }
    }

    /* fetchConfiguration */
    private fun fetchConfiguration() = viewModelScope.launch {
        fetchConfigurationUseCase.invoke(Unit).onSuccess {
            reduce { copy(appConfiguration = it) }
        }.onFailure {
            Log.e(tag, "fetchConfiguration : $it")
        }
    }

    private fun fetchIsOfflineFirstOpen() {
        viewModelScope.launch {
            isOfflineFirstOpenUseCase.invoke(Unit).onSuccess {
                if (!it) return@launch
                reduce { copy(isShowOnboardingDialog = true) }
            }.onFailure {
                logger.e(tag, "fetchIsFirstOpen : $it")
            }
        }
    }

    private fun setIsOfflineFirstOpen() {
        viewModelScope.launch {
            setIsOfflineFirstOpenUseCase.invoke(false).onSuccess {
                logger.d(tag, "setIsFirstOpen : $it")
            }.onFailure {
                logger.e(tag, "setIsFirstOpen : $it")
            }
        }
    }

    private fun refresh() {
        reduce { copy(isRefreshing = true) }
        fetchOfflineDefects()
    }

    private fun onClickSetting() {
        postIntent(OfflineDefectIntent.HideOnboardingDialog)
        postSideEffect { OfflineDefectSideEffect.NavigateToSetting }
    }

    private fun onClickUpload() {
        setUploadDialog(true)
    }

    private fun setUploadDialog(isVisible: Boolean) {
        reduce { copy(isShowUploadDialog = isVisible) }
    }

    private fun navigateToOfflineDefectDetail(defectItem: DefectItem) {
        postSideEffect { OfflineDefectSideEffect.NavigateToOfflineDefectDetail(defectItem) }
    }

    private fun setOnboardingDialog(isVisible: Boolean) {
        reduce { copy(isShowOnboardingDialog = isVisible) }
        if (!isVisible) {
            setIsOfflineFirstOpen()
        }
    }

    private fun uploadAllOfflineDefect() {
        reduce { copy(isShowUploadDialog = false, isShowUploadProgressDialog = true) }
        uploadAllOfflineDefectToRemote()
    }

    private fun uploadAllOfflineDefectToRemote() {
        viewModelScope.launch {
            uploadAllOfflineDefectToRemoteUseCase.invoke(concurrency = 5)
                .catch {
                    logger.e(tag, "uploadAllOfflineDefectToRemote : $it", it)
                }.collect {
                    reduce { copy(offlineDefectProgress = it, uploadProgress = it.uploaded.toFloat() / it.total) }
                    if (it.uploaded == it.total) {
                        reduce { copy(offlineDefectProgress = null, isShowUploadProgressDialog = false) }
                    }
                }
        }
    }

}