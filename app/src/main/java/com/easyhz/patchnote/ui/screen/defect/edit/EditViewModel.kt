package com.easyhz.patchnote.ui.screen.defect.edit

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.handleError
import com.easyhz.patchnote.core.common.util.Generate
import com.easyhz.patchnote.core.common.util.getPostposition
import com.easyhz.patchnote.core.common.util.log.Logger
import com.easyhz.patchnote.core.common.util.resource.ResourceHelper
import com.easyhz.patchnote.core.common.util.serializable.SerializableHelper
import com.easyhz.patchnote.core.designSystem.component.bottomSheet.ImageBottomSheetType
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.defect.EntryDefectParam
import com.easyhz.patchnote.core.model.error.DialogAction
import com.easyhz.patchnote.core.model.error.DialogMessage
import com.easyhz.patchnote.core.model.image.DefectImage
import com.easyhz.patchnote.core.model.image.toDefectImages
import com.easyhz.patchnote.domain.usecase.defect.FetchDefectUseCase
import com.easyhz.patchnote.domain.usecase.image.GetDefectImagesUseCase
import com.easyhz.patchnote.domain.usecase.image.GetTakePictureUriUseCase
import com.easyhz.patchnote.domain.usecase.image.RotateImageUseCase
import com.easyhz.patchnote.ui.screen.defect.edit.contract.DefectEditIntent
import com.easyhz.patchnote.ui.screen.defect.edit.contract.DefectEditSideEffect
import com.easyhz.patchnote.ui.screen.defect.edit.contract.DefectEditState
import com.easyhz.patchnote.ui.screen.defect.edit.contract.DefectEditState.Companion.deleteImage
import com.easyhz.patchnote.ui.screen.defect.edit.contract.DefectEditState.Companion.updateImages
import kotlinx.coroutines.launch

abstract class EditViewModel(
    private val logger: Logger,
    private val savedStateHandle: SavedStateHandle,
    private val serializableHelper: SerializableHelper,
    private val resourceHelper: ResourceHelper,
    private val getTakePictureUriUseCase: GetTakePictureUriUseCase,
    private val rotateImageUseCase: RotateImageUseCase,
    private val getDefectImagesUseCase: GetDefectImagesUseCase,
    private val fetchDefectUseCase: FetchDefectUseCase,
): BaseViewModel<DefectEditState, DefectEditIntent, DefectEditSideEffect>(
    initialState = DefectEditState.init()
) {
    abstract val tag: String
    private var takePictureUri = mutableStateOf(Uri.EMPTY)
    var hasUploadHistory by mutableStateOf(false)

    override fun handleIntent(intent: DefectEditIntent) {
        when(intent) {
            is DefectEditIntent.ChangeEntryContent -> { reduce { copy(entryContent = intent.value) } }
            is DefectEditIntent.ChangeStateImageBottomSheet -> { changeStateImageBottomSheet(intent.isShow) }
            is DefectEditIntent.ClickImageBottomSheet -> { onClickImageBottomSheet(intent.imageBottomSheetType) }
            is DefectEditIntent.PickImages -> { updateEntryImages(intent.images) }
            is DefectEditIntent.TakePicture -> { updateTakePicture(intent.isUsed) }
            is DefectEditIntent.DeleteImage -> { deleteEntryImage(intent.image) }
            is DefectEditIntent.ClickReceipt -> { checkDefect(intent.entryItem, intent.invalidEntry) }
            is DefectEditIntent.NavigateToUp -> { handleNavigateToUp() }
            is DefectEditIntent.SetDialog -> { setDialog(intent.message) }
            is DefectEditIntent.SetLoading -> { setLoading(intent.isLoading) }
            is DefectEditIntent.UpdateDefect -> { updateDefect() }
            is DefectEditIntent.HideEntryDialog -> { hideEntryDialog() }
        }
    }

    init {
        initData()
    }

    private fun initData() {
        val defectItemArgs: String? = savedStateHandle["defectItem"]
        val defectItem = serializableHelper.deserialize(defectItemArgs, DefectItem::class.java) ?: return navigateUp()

        reduce { copy(defectItem = defectItem, entryContent = defectItem.beforeDescription, entryItem = entryItem) }
        setUp(defectItem)
    }

    private fun setUp(defectItem: DefectItem) {
        viewModelScope.launch {
            setLoading(true)
            getDefectImagesUseCase.invoke(defectItem.beforeImageUrls).onSuccess {
                val entryItem = linkedMapOf(
                    CategoryType.SITE to TextFieldValue(defectItem.site),
                    CategoryType.BUILDING to TextFieldValue(defectItem.building),
                    CategoryType.UNIT to TextFieldValue(defectItem.unit),
                    CategoryType.SPACE to TextFieldValue(defectItem.space),
                    CategoryType.PART to TextFieldValue(defectItem.part),
                    CategoryType.WORK_TYPE to TextFieldValue(defectItem.workType),
                )
                reduce { copy(images = it, isSuccessGetData = true, isLoading = false) }
                postSideEffect { DefectEditSideEffect.SendEntryItem(entryItem) }
            }.onFailure {
                logger.e(tag, "setUp : $it")
                setLoading(false)
                setDialog(DialogMessage(title = resourceHelper.getString(it.handleError())))
            }
        }
    }

    /* 이미지 바텀시트 상태 변경 */
    private fun changeStateImageBottomSheet(isShow: Boolean) {
        reduce { copy(isShowImageBottomSheet = isShow) }
    }

    /* 이미지 바텀시트 클릭 */
    private fun onClickImageBottomSheet(imageBottomSheetType: ImageBottomSheetType) {
        when(imageBottomSheetType) {
            ImageBottomSheetType.GALLERY -> { launchGallery() }
            ImageBottomSheetType.CAMERA -> { launchCamera() }
        }
    }

    /* 갤러리 실행 */
    private fun launchGallery() {
        postSideEffect { DefectEditSideEffect.NavigateToGallery }
    }

    /* 카메라 실행 : uri 생성 */
    private fun launchCamera() = viewModelScope.launch {
        getTakePictureUriUseCase.invoke(Unit)
            .onSuccess {
                takePictureUri.value = it
                postSideEffect { DefectEditSideEffect.NavigateToCamera(it) }
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
        reduce { updateImages(newImages = listOf(takePictureUri.value).toDefectImages()) }
        rotateImage(takePictureUri.value)
    }

    /* 이미지 삭제 */
    private fun deleteEntryImage(image: DefectImage) {
        reduce { deleteImage(image) }
    }

    /* 하자 등록 */
    private fun checkDefect(entryItem: LinkedHashMap<CategoryType, TextFieldValue>, invalidEntry: CategoryType?) = viewModelScope.launch {
        clearFocus()
        if (!isValidDefect(invalidEntry)) return@launch
        if (!isValidImage()) return@launch
        reduce { copy(entryItem = entryItem, isShowEntryDialog = true) }
    }

    abstract fun updateDefect()

    fun getEntryParam(): EntryDefectParam {
        val entryItem = currentState.entryItem
        return EntryDefectParam(
            id = currentState.defectItem?.id ?: Generate.randomUUID(),
            site = entryItem[CategoryType.SITE]?.text.orEmpty(),
            building = entryItem[CategoryType.BUILDING]?.text.orEmpty(),
            unit = entryItem[CategoryType.UNIT]?.text.orEmpty(),
            space = entryItem[CategoryType.SPACE]?.text.orEmpty(),
            part = entryItem[CategoryType.PART]?.text.orEmpty(),
            workType = entryItem[CategoryType.WORK_TYPE]?.text.orEmpty(),
            beforeDescription = currentState.entryContent,
            beforeImageUris = currentState.images.map { it.uri },
            creationTime = currentState.defectItem?.requestDate
        )
    }

    /* 하자 등록 유효성 검사 */
    private fun isValidDefect(invalidEntry: CategoryType?): Boolean {
        invalidEntry?.let { type ->
            val valueString = resourceHelper.getString(type.nameId) + getPostposition(type)
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
        navigateUp()
    }

    /* 뒤로가기 */
    private fun navigateUp() {
        postSideEffect { DefectEditSideEffect.NavigateToUp }
    }

    /* 홈으로 */
    private fun navigateHome() {
        viewModelScope.launch {
            if (currentState.defectItem != null && currentState.defectItem?.id != null) {
                setLoading(true)
                fetchDefectUseCase.invoke(currentState.defectItem?.id!!).onSuccess {
                    postSideEffect { DefectEditSideEffect.NavigateToDefectDetail(it.defectItem) }
                }.onFailure {
                    logger.e(tag, "navigateToDefectDetail : $it")
                    navigateUp()
                }.also {
                    setLoading(false)
                }
            } else {
                navigateUp()
            }
        }
    }

    /* 포커스 해제 */
    private fun clearFocus() {
        postSideEffect { DefectEditSideEffect.ClearFocus }
    }

    /* 에러 다이얼로그 */
    fun setDialog(message: DialogMessage?) {
        val action = message?.let { null } ?: currentState.dialogMessage?.action
        reduce { copy(dialogMessage = message) }
        action?.let {
            when(it) {
                is DialogAction.CustomAction -> { navigateHome() }
                DialogAction.NavigateUp -> navigateUp()
                else -> { }
            }
        }
    }

    /* 로딩 */
    fun setLoading(isLoading: Boolean) {
        if (!currentState.isSuccessGetData && !isLoading) return
        reduce { copy(isLoading = isLoading) }
    }


    /* 이미지 회전 */
    private fun rotateImage(uri: Uri) = viewModelScope.launch {
        rotateImageUseCase.invoke(uri)
            .onFailure {
                logger.e(tag, "rotateImage : $it")
            }
    }

    /* 접수 다이얼로그 숨기기 */
    fun hideEntryDialog() {
        reduce { copy(isShowEntryDialog = false) }
    }
}