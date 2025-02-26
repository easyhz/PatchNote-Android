package com.easyhz.patchnote.ui.screen.defect.defectDetail

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.util.serializable.SerializableHelper
import com.easyhz.patchnote.core.designSystem.util.bottomSheet.DefectDetailBottomSheet
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.defect.DefectMainItem
import com.easyhz.patchnote.core.model.error.DialogAction
import com.easyhz.patchnote.core.model.error.DialogMessage
import com.easyhz.patchnote.domain.usecase.defect.DeleteDefectUseCase
import com.easyhz.patchnote.domain.usecase.sign.GetUserIdUseCase
import com.easyhz.patchnote.ui.screen.defectDetail.contract.DetailIntent
import com.easyhz.patchnote.ui.screen.defectDetail.contract.DetailSideEffect
import com.easyhz.patchnote.ui.screen.defectDetail.contract.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DefectDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val savedStateHandle: SavedStateHandle,
    private val serializableHelper: SerializableHelper,
    private val deleteDefectUseCase: DeleteDefectUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
) : BaseViewModel<DetailState, DetailIntent, DetailSideEffect>(
    initialState = DetailState.init()
) {
    override fun handleIntent(intent: DetailIntent) {
        when (intent) {
            is DetailIntent.NavigateToUp -> navigateToUp()
            is DetailIntent.CompleteDefect -> navigateToDefectCompletion()
            is DetailIntent.ChangeStateBottomSheet -> setBottomSheet(intent.isShowBottomSheet)
            is DetailIntent.ClickBottomSheetItem -> clickBottomSheetItem(intent.bottomSheetType)
            is DetailIntent.ShowError -> setErrorDialog(intent.message)
            is DetailIntent.ShowDeleteDialog -> setDeleteDialog(intent.isShow)
            is DetailIntent.SetLoading -> reduce { copy(isLoading = intent.isLoading) }
            is DetailIntent.DeleteDefect -> deleteDefect()
        }
    }

    init {
        initData()
    }

    private fun initData() {
        val defectItemArgs: String? = savedStateHandle["defectItem"]
        val defectItem = serializableHelper.deserialize(defectItemArgs, DefectItem::class.java) ?: return navigateToUp()

        viewModelScope.launch {
            getUserIdUseCase.invoke(Unit).onSuccess {
                reduce { copy(defectItem = defectItem, isOwner = it == defectItem.requesterId) }
            }
        }
    }

    private fun navigateToUp() {
        postSideEffect { DetailSideEffect.NavigateToUp }
    }

    private fun navigateToDefectCompletion() {
        currentState.defectItem?.let { item ->
            val defectMainItem = DefectMainItem(
                id = item.id,
                site = item.site,
                building = item.building,
                unit = item.unit,
                space = item.space,
                part = item.part,
                workType = item.workType,
                requesterName = item.requesterName,
                requestDate = item.requestDate
            )
            postSideEffect { DetailSideEffect.NavigateToDefectCompletion(defectMainItem) }
        }
    }

    private fun clickBottomSheetItem(item: DefectDetailBottomSheet) {
        when (item) {
            DefectDetailBottomSheet.DELETE -> {
                setBottomSheet(false)
                setDeleteDialog(true)
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
        deleteDefectUseCase.invoke(currentState.defectItem!!.id).onSuccess {
            setErrorDialog(
                DialogMessage(
                    title = context.getString(R.string.defect_delete_success),
                    action = DialogAction.NavigateUp
                )
            )
        }.onFailure {
            setErrorDialog(
                DialogMessage(
                    title = context.getString(R.string.defect_delete_error),
                    message = context.getString(R.string.defect_delete_error_content)
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