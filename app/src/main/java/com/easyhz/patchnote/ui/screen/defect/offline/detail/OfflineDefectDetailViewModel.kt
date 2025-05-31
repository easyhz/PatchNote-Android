package com.easyhz.patchnote.ui.screen.defect.offline.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.util.resource.ResourceHelper
import com.easyhz.patchnote.core.common.util.serializable.SerializableHelper
import com.easyhz.patchnote.core.designSystem.util.bottomSheet.DefectDetailBottomSheet
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.error.DialogAction
import com.easyhz.patchnote.core.model.error.DialogMessage
import com.easyhz.patchnote.domain.usecase.defect.offline.DeleteOfflineDefectUseCase
import com.easyhz.patchnote.domain.usecase.defect.offline.UploadOfflineDefectToRemoteUseCase
import com.easyhz.patchnote.ui.screen.defect.offline.detail.contract.OfflineDefectDetailIntent
import com.easyhz.patchnote.ui.screen.defect.offline.detail.contract.OfflineDefectDetailSideEffect
import com.easyhz.patchnote.ui.screen.defect.offline.detail.contract.OfflineDefectDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfflineDefectDetailViewModel @Inject constructor(
    private val resourceHelper: ResourceHelper,
    private val savedStateHandle: SavedStateHandle,
    private val serializableHelper: SerializableHelper,
    private val deleteOfflineDefectUseCase: DeleteOfflineDefectUseCase,
    private val uploadOfflineDefectToRemoteUseCase: UploadOfflineDefectToRemoteUseCase
): BaseViewModel<OfflineDefectDetailState, OfflineDefectDetailIntent, OfflineDefectDetailSideEffect>(
    initialState = OfflineDefectDetailState.init()
) {
    override fun handleIntent(intent: OfflineDefectDetailIntent) {
        when (intent) {
            is OfflineDefectDetailIntent.NavigateToUp -> navigateToUp()
            is OfflineDefectDetailIntent.ChangeStateBottomSheet -> setBottomSheet(intent.isShowBottomSheet)
            is OfflineDefectDetailIntent.ClickBottomSheetItem -> clickBottomSheetItem(intent.bottomSheetType)
            is OfflineDefectDetailIntent.ShowError -> setErrorDialog(intent.message)
            is OfflineDefectDetailIntent.ShowDeleteDialog -> setDeleteDialog(intent.isShow)
            is OfflineDefectDetailIntent.SetLoading -> reduce { copy(isLoading = intent.isLoading) }
            is OfflineDefectDetailIntent.DeleteDefect -> deleteDefect()
            is OfflineDefectDetailIntent.HideUploadSuccessDialog -> hideUploadSuccessDialog()
            is OfflineDefectDetailIntent.ClickMainButton -> setUploadDialog(true)
            is OfflineDefectDetailIntent.HideUploadDialog -> setUploadDialog(false)
            is OfflineDefectDetailIntent.UploadOfflineDefect -> uploadOfflineDefect()
        }
    }


    init {
        initData()
    }

    private fun initData() {
        val defectItemArgs: String? = savedStateHandle["defectItem"]
        val defectItem = serializableHelper.deserialize(defectItemArgs, DefectItem::class.java) ?: return navigateToUp()

        viewModelScope.launch {
            reduce { copy(defectItem = defectItem.toDecode()) }
        }
    }

    private fun navigateToUp() {
        postSideEffect { OfflineDefectDetailSideEffect.NavigateToUp }
    }

    private fun uploadOfflineDefect() = viewModelScope.launch {
        if (currentState.defectItem == null) return@launch
        reduce { copy(isLoading = true, isShowUploadDialog = false) }
        uploadOfflineDefectToRemoteUseCase.invoke(currentState.defectItem!!).onSuccess {
            reduce { copy(isShowUploadSuccessDialog = true) }
        }.onFailure {
            setErrorDialog(
                DialogMessage(
                    title = resourceHelper.getString(R.string.offline_defect_upload_error),
                    message = resourceHelper.getString(R.string.offline_defect_upload_error_content)
                )
            )
        }.also {
            setLoading(false)
        }
    }

    private fun hideUploadSuccessDialog() {
        reduce { copy(isShowUploadSuccessDialog = false) }
        navigateToUp()
    }

    private fun setUploadDialog(isShow: Boolean) {
        reduce { copy(isShowUploadDialog = isShow) }
    }

    private fun clickBottomSheetItem(item: DefectDetailBottomSheet) {
        when (item) {
            DefectDetailBottomSheet.DELETE -> {
                setBottomSheet(false)
                setDeleteDialog(true)
            }
            DefectDetailBottomSheet.EDIT -> {
                setBottomSheet(false)
                if (currentState.defectItem == null) {
                    navigateToUp()
                    return
                }
                postSideEffect { OfflineDefectDetailSideEffect.NavigateToDefectEdit(currentState.defectItem!!) }
            }
        }
    }

    private fun setBottomSheet(isShow: Boolean) {
        reduce { copy(isShowBottomSheet = isShow) }
    }

    private fun setDeleteDialog(isShow: Boolean) {
        reduce { copy(isShowDeleteDialog = isShow) }
    }

    private fun deleteDefect() = viewModelScope.launch {
        if (currentState.defectItem == null) return@launch
        setLoading(true)
        setDeleteDialog(false)
        deleteOfflineDefectUseCase.invoke(currentState.defectItem!!.id).onSuccess {
            setErrorDialog(
                DialogMessage(
                    title = resourceHelper.getString(R.string.defect_delete_success),
                    action = DialogAction.NavigateUp
                )
            )
        }.onFailure {
            setErrorDialog(
                DialogMessage(
                    title = resourceHelper.getString(R.string.defect_delete_error),
                    message = resourceHelper.getString(R.string.defect_delete_error_content)
                )
            )
        }.also {
            setLoading(false)
        }
    }

    /* 에러 다이얼로그 */
    private fun setErrorDialog(message: DialogMessage?) {
        val action = message?.let { null } ?: currentState.dialogMessage?.action
        reduce { copy(dialogMessage = message) }
        action?.let {
            when (it) {
                DialogAction.NavigateUp -> navigateToUp()
                else -> {}
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        reduce { copy(isLoading = isLoading) }
    }
}