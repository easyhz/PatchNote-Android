package com.easyhz.patchnote.ui.screen.defectEntry

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.handleError
import com.easyhz.patchnote.core.common.util.Generate
import com.easyhz.patchnote.core.common.util.getPostposition
import com.easyhz.patchnote.core.designSystem.component.bottomSheet.ImageBottomSheetType
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.defect.EntryDefectParam
import com.easyhz.patchnote.core.model.error.ErrorAction
import com.easyhz.patchnote.core.model.error.ErrorMessage
import com.easyhz.patchnote.core.model.image.DefectImage
import com.easyhz.patchnote.core.model.image.toDefectImages
import com.easyhz.patchnote.domain.usecase.defect.CreateDefectUseCase
import com.easyhz.patchnote.domain.usecase.image.GetTakePictureUriUseCase
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntryIntent
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntrySideEffect
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntryState
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntryState.Companion.deleteImage
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntryState.Companion.updateImages
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DefectEntryViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getTakePictureUriUseCase: GetTakePictureUriUseCase,
    private val createDefectUseCase: CreateDefectUseCase
): BaseViewModel<DefectEntryState, DefectEntryIntent, DefectEntrySideEffect>(
    initialState = DefectEntryState.init()
) {
    private val tag = "DefectEntryViewModel"
    private var takePictureUri = mutableStateOf(Uri.EMPTY)

    override fun handleIntent(intent: DefectEntryIntent) {
        when(intent) {
            is DefectEntryIntent.ChangeEntryContent -> { reduce { copy(entryContent = intent.value) } }
            is DefectEntryIntent.ChangeStateImageBottomSheet -> { changeStateImageBottomSheet(intent.isShow) }
            is DefectEntryIntent.ClickImageBottomSheet -> { onClickImageBottomSheet(intent.imageBottomSheetType) }
            is DefectEntryIntent.PickImages -> { updateEntryImages(intent.images) }
            is DefectEntryIntent.TakePicture -> { updateTakePicture(intent.isUsed) }
            is DefectEntryIntent.DeleteImage -> { deleteEntryImage(intent.image) }
            is DefectEntryIntent.ClickReceipt -> { createDefect(intent.entryItem, intent.invalidEntry) }
            is DefectEntryIntent.NavigateToUp -> { navigateUp() }
            is DefectEntryIntent.ShowError -> { setDialog(intent.message) }
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
                Log.e(tag, "launchCamera : $it")
                setDialog(ErrorMessage(title = context.getString(it.handleError())))
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
        val param = EntryDefectParam(
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
        createDefectUseCase.invoke(param)
            .onSuccess {
                navigateHome()
            }
            .onFailure {
                Log.e(tag, "createDefect : $it")
                setDialog(ErrorMessage(title = context.getString(R.string.error_create_defect_failure)))
            }
    }

    /* 하자 등록 유효성 검사 */
    private fun isValidDefect(invalidEntry: CategoryType?): Boolean {
        invalidEntry?.let { type ->
            val valueString = context.getString(type.nameId) + getPostposition(type)
            setDialog(
                ErrorMessage(title = context.getString(R.string.category_empty, valueString))
            )
            return false
        }

        return true
    }

    /* 이미지 유효성 검사 */
    private fun isValidImage(): Boolean {
        if (currentState.images.isEmpty()) {
            setDialog(ErrorMessage(title = context.getString(R.string.defect_entry_image_placeholder)))
            return false
        }

        return true
    }

    /* 뒤로가기 */
    private fun navigateUp() {
        clearFocus()
        postSideEffect { DefectEntrySideEffect.NavigateToUp }
    }

    /* 홈으로 */
    private fun navigateHome() {
        clearFocus()
        postSideEffect { DefectEntrySideEffect.NavigateToHome }
    }

    /* 포커스 해제 */
    private fun clearFocus() {
        postSideEffect { DefectEntrySideEffect.ClearFocus }
    }

    /* 에러 다이얼로그 */
    private fun setDialog(message: ErrorMessage?) {
        val action = message?.let { null } ?: currentState.errorMessage?.action
        reduce { copy(errorMessage = message) }
        action?.let {
            when(it) {
                ErrorAction.NAVIGATE_UP -> navigateUp()
                else -> { }
            }
        }
    }
}