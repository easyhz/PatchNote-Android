package com.easyhz.patchnote.ui.screen.defect.defectEntry

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.handleError
import com.easyhz.patchnote.core.common.util.Generate
import com.easyhz.patchnote.core.common.util.getPostposition
import com.easyhz.patchnote.core.common.util.log.Logger
import com.easyhz.patchnote.core.common.util.resource.ResourceHelper
import com.easyhz.patchnote.core.designSystem.component.bottomSheet.ImageBottomSheetType
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.defect.EntryDefectParam
import com.easyhz.patchnote.core.model.error.DialogAction
import com.easyhz.patchnote.core.model.error.DialogMessage
import com.easyhz.patchnote.core.model.image.DefectImage
import com.easyhz.patchnote.core.model.image.toDefectImages
import com.easyhz.patchnote.domain.usecase.defect.defect.CreateDefectUseCase
import com.easyhz.patchnote.domain.usecase.defect.offline.SaveOfflineDefectUseCase
import com.easyhz.patchnote.domain.usecase.image.GetTakePictureUriUseCase
import com.easyhz.patchnote.domain.usecase.image.RotateImageUseCase
import com.easyhz.patchnote.ui.screen.defect.defectEntry.contract.DefectEntryIntent
import com.easyhz.patchnote.ui.screen.defect.defectEntry.contract.DefectEntrySideEffect
import com.easyhz.patchnote.ui.screen.defect.defectEntry.contract.DefectEntryState
import com.easyhz.patchnote.ui.screen.defect.defectEntry.contract.DefectEntryState.Companion.deleteImage
import com.easyhz.patchnote.ui.screen.defect.defectEntry.contract.DefectEntryState.Companion.updateImages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DefectEntryViewModel @Inject constructor(
    private val resourceHelper: ResourceHelper,
    private val logger: Logger,
    private val getTakePictureUriUseCase: GetTakePictureUriUseCase,
    private val createDefectUseCase: CreateDefectUseCase,
    private val rotateImageUseCase: RotateImageUseCase,
    private val saveOfflineDefectUseCase: SaveOfflineDefectUseCase,
): BaseViewModel<DefectEntryState, DefectEntryIntent, DefectEntrySideEffect>(
    initialState = DefectEntryState.init()
) {
    private val tag = "DefectEntryViewModel"
    private var takePictureUri = mutableStateOf(Uri.EMPTY)
    private var hasUploadHistory by mutableStateOf(false)

    override fun handleIntent(intent: DefectEntryIntent) {
        when(intent) {
            is DefectEntryIntent.ChangeEntryContent -> { reduce { copy(entryContent = intent.value) } }
            is DefectEntryIntent.ChangeStateImageBottomSheet -> { changeStateImageBottomSheet(intent.isShow) }
            is DefectEntryIntent.ClickImageBottomSheet -> { onClickImageBottomSheet(intent.imageBottomSheetType) }
            is DefectEntryIntent.PickImages -> { updateEntryImages(intent.images) }
            is DefectEntryIntent.TakePicture -> { updateTakePicture(intent.isUsed) }
            is DefectEntryIntent.DeleteImage -> { deleteEntryImage(intent.image) }
            is DefectEntryIntent.ClickReceipt -> { createDefect(intent.entryItem, intent.invalidEntry) }
            is DefectEntryIntent.NavigateToUp -> { handleNavigateToUp() }
            is DefectEntryIntent.SetDialog -> { setDialog(intent.message) }
            is DefectEntryIntent.SetLoading -> { setLoading(intent.isLoading) }
            is DefectEntryIntent.SaveDefect -> { saveDefect() }
            is DefectEntryIntent.SaveOfflineDefect -> { saveOfflineDefect() }
            is DefectEntryIntent.HideEntryDialog -> { hideEntryDialog() }
        }
    }

    /* 이미지 바텀시트 상태 변경 */
    private fun changeStateImageBottomSheet(isShow: Boolean) {
        reduce { copy(isShowImageBottomSheet = isShow) }
    }

    /* 이미지 바텀시트 클릭 */
    private fun onClickImageBottomSheet(imageBottomSheetType: ImageBottomSheetType) {
        clearFocus()
        when(imageBottomSheetType) {
            ImageBottomSheetType.GALLERY -> { launchGallery() }
            ImageBottomSheetType.CAMERA -> { launchCamera() }
        }
    }

    /* 갤러리 실행 */
    private fun launchGallery() {
        postSideEffect { DefectEntrySideEffect.NavigateToGallery }
    }

    /* 카메라 실행 : uri 생성 */
    private fun launchCamera() = viewModelScope.launch {
        getTakePictureUriUseCase.invoke(Unit)
            .onSuccess {
                takePictureUri.value = it
                postSideEffect { DefectEntrySideEffect.NavigateToCamera(it) }
            }
            .onFailure {
                logger.e(tag, "launchCamera : $it")
                setDialog(DialogMessage(title = resourceHelper.getString(it.handleError())))
            }
    }

    /* 이미지 업데이트 */
    private fun updateEntryImages(newImages: List<Uri>) {
        reduce { updateImages(newImages.toDefectImages()) }
    }

    /* 찍은 사진 추가 */
    private fun updateTakePicture(isUsed: Boolean) {
        if (!isUsed) return
        
        // 포커스를 먼저 해제
        clearFocus()
        
        // 이미지 상태 업데이트
        reduce { updateImages(newImages = listOf(takePictureUri.value).toDefectImages()) }
        
        // 이미지 회전을 지연 처리
        viewModelScope.launch {
            kotlinx.coroutines.delay(100) // 화면 안정화를 위한 짧은 지연
            rotateImage(takePictureUri.value)
        }
    }

    /* 이미지 삭제 */
    private fun deleteEntryImage(image: DefectImage) {
        reduce { deleteImage(image) }
    }

    /* 하자 등록 */
    private fun createDefect(entryItem: LinkedHashMap<CategoryType, TextFieldValue>, invalidEntry: CategoryType?) = viewModelScope.launch {
        clearFocus()
        if (!isValidDefect(invalidEntry)) return@launch
        if (!isValidImage()) return@launch
        reduce { copy(entryItem = entryItem, isShowEntryDialog = true) }
    }

    private fun saveDefect() {
        viewModelScope.launch {
            hideEntryDialog()
            setLoading(true)
            createDefectUseCase.invoke(getEntryParam())
                .onSuccess {
                    setDialog(
                        DialogMessage(
                            title = resourceHelper.getString(R.string.success_create_defect),
                            action = DialogAction.Clear
                        )
                    )
                    hasUploadHistory = true
                }
                .onFailure {
                    logger.e(tag, "createDefect : $it")
                    setDialog(DialogMessage(title = resourceHelper.getString(R.string.error_create_defect_failure)))
                }.also {
                    setLoading(false)
                }
        }
    }

    private fun saveOfflineDefect() {
        viewModelScope.launch {
            setLoading(true)
            hideEntryDialog()
            saveOfflineDefectUseCase.invoke(getEntryParam())
                .onSuccess {
                    setDialog(
                        DialogMessage(
                            title = resourceHelper.getString(R.string.success_create_offline_defect),
                            action = DialogAction.Clear
                        )
                    )
                }.onFailure {
                    logger.e(tag, "saveOfflineDefect : $it")
                    setDialog(DialogMessage(title = resourceHelper.getString(R.string.error_create_defect_failure)))
                }.also {
                    setLoading(false)
                }
        }
    }

    private fun getEntryParam(): EntryDefectParam {
        val entryItem = currentState.entryItem
        return EntryDefectParam(
            id = Generate.randomUUID(),
            site = entryItem[CategoryType.SITE]?.text.orEmpty(),
            building = entryItem[CategoryType.BUILDING]?.text.orEmpty(),
            unit = entryItem[CategoryType.UNIT]?.text.orEmpty(),
            space = entryItem[CategoryType.SPACE]?.text.orEmpty(),
            part = entryItem[CategoryType.PART]?.text.orEmpty(),
            workType = entryItem[CategoryType.WORK_TYPE]?.text.orEmpty(),
            beforeDescription = currentState.entryContent,
            beforeImageUris = currentState.images.map { it.uri }
        )
    }

    /* 하자 등록 유효성 검사 */
    private fun isValidDefect(invalidEntry: CategoryType?): Boolean {
        invalidEntry?.let { type ->
            val valueString = getPostposition((resourceHelper.getString(type.nameId)))
            setDialog(DialogMessage(title = resourceHelper.getString(R.string.category_empty, valueString)))
            return false
        }

        return true
    }

    /* 이미지 유효성 검사 */
    private fun isValidImage(): Boolean {
        if (currentState.images.isEmpty()) {
            setDialog(DialogMessage(title = resourceHelper.getString(R.string.defect_entry_image_placeholder)))
            return false
        }

        return true
    }

    /* 뒤로가기 핸들 */
    private fun handleNavigateToUp() {
        clearFocus()
        if (hasUploadHistory) navigateHome()
        else navigateUp()
    }

    /* 뒤로가기 */
    private fun navigateUp() {
        postSideEffect { DefectEntrySideEffect.NavigateToUp }
    }

    /* 홈으로 */
    private fun navigateHome() {
        postSideEffect { DefectEntrySideEffect.NavigateToHome }
    }

    /* 포커스 해제 */
    private fun clearFocus() {
        postSideEffect { DefectEntrySideEffect.ClearFocus }
    }

    /* 에러 다이얼로그 */
    private fun setDialog(message: DialogMessage?) {
        val action = message?.let { null } ?: currentState.dialogMessage?.action
        reduce { copy(dialogMessage = message) }
        action?.let {
            when(it) {
                DialogAction.NavigateUp -> navigateUp()
                DialogAction.Clear -> clearData()
                else -> { }
            }
        }
    }

    /* 로딩 */
    private fun setLoading(isLoading: Boolean) {
        reduce { copy(isLoading = isLoading) }
    }

    /* 데이터 클리어 */
    private fun clearData() {
        clearFocus()
        sendClear()
        reduce { copy(entryContent = "", images = emptyList()) }
    }

    /* sendClear */
    private fun sendClear() {
        postSideEffect { DefectEntrySideEffect.SendClear }
    }

    /* 이미지 회전 */
    private fun rotateImage(uri: Uri) = viewModelScope.launch {
        rotateImageUseCase.invoke(uri)
            .onFailure {
                logger.e(tag, "rotateImage : $it")
            }
    }

    /* 접수 다이얼로그 숨기기 */
    private fun hideEntryDialog() {
        reduce { copy(isShowEntryDialog = false) }
    }
}
