package com.easyhz.patchnote.ui.screen.defectCompletion

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.handleError
import com.easyhz.patchnote.core.designSystem.component.bottomSheet.ImageBottomSheetType
import com.easyhz.patchnote.core.model.defect.DefectCompletionParam
import com.easyhz.patchnote.core.model.error.DialogAction
import com.easyhz.patchnote.core.model.error.DialogMessage
import com.easyhz.patchnote.core.model.image.DefectImage
import com.easyhz.patchnote.core.model.image.toDefectImages
import com.easyhz.patchnote.domain.usecase.defect.UpdateDefectCompletionUseCase
import com.easyhz.patchnote.domain.usecase.image.GetTakePictureUriUseCase
import com.easyhz.patchnote.domain.usecase.image.RotateImageUseCase
import com.easyhz.patchnote.ui.screen.defectCompletion.contract.DefectCompletionIntent
import com.easyhz.patchnote.ui.screen.defectCompletion.contract.DefectCompletionSideEffect
import com.easyhz.patchnote.ui.screen.defectCompletion.contract.DefectCompletionState
import com.easyhz.patchnote.ui.screen.defectCompletion.contract.DefectCompletionState.Companion.deleteImage
import com.easyhz.patchnote.ui.screen.defectCompletion.contract.DefectCompletionState.Companion.updateImages
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DefectCompletionViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getTakePictureUriUseCase: GetTakePictureUriUseCase,
    private val updateDefectCompletionUseCase: UpdateDefectCompletionUseCase,
    private val rotateImageUseCase: RotateImageUseCase
) : BaseViewModel<DefectCompletionState, DefectCompletionIntent, DefectCompletionSideEffect>(
    initialState = DefectCompletionState.init(),
) {
    private val tag = "DefectCompletionViewModel"
    private var takePictureUri = mutableStateOf(Uri.EMPTY)

    override fun handleIntent(intent: DefectCompletionIntent) {
        when (intent) {
            is DefectCompletionIntent.ChangeCompletionContent -> {
                onChangeCompletionContent(intent.value)
            }

            is DefectCompletionIntent.ChangeStateImageBottomSheet -> {
                onChangeStateImageBottomSheet(intent.isShow)
            }

            is DefectCompletionIntent.ClickImageBottomSheet -> {
                onClickImageBottomSheet(intent.imageBottomSheetType)
            }

            is DefectCompletionIntent.PickImages -> {
                updateEntryImages(intent.images)
            }

            is DefectCompletionIntent.TakePicture -> {
                updateTakePicture(intent.isUsed)
            }

            is DefectCompletionIntent.DeleteImage -> {
                deleteEntryImage(intent.image)
            }

            is DefectCompletionIntent.ClickCompletion -> {
                clickCompletion(intent.id)
            }

            is DefectCompletionIntent.NavigateToUp -> {
                navigateUp()
            }

            is DefectCompletionIntent.ShowError -> {
                setDialog(intent.message)
            }

            is DefectCompletionIntent.SetLoading -> {
                setLoading(intent.isLoading)
            }
        }
    }

    private fun onChangeCompletionContent(newValue: String) {
        reduce { copy(completionContent = newValue) }
    }

    private fun onChangeStateImageBottomSheet(isShow: Boolean) {
        reduce { copy(isShowImageBottomSheet = isShow) }
    }

    private fun onClickImageBottomSheet(imageBottomSheetType: ImageBottomSheetType) {
        when (imageBottomSheetType) {
            ImageBottomSheetType.GALLERY -> {
                launchGallery()
            }

            ImageBottomSheetType.CAMERA -> {
                launchCamera()
            }
        }
    }

    /* 갤러리 실행 */
    private fun launchGallery() {
        postSideEffect { DefectCompletionSideEffect.NavigateToGallery }
    }

    /* 카메라 실행 : uri 생성 */
    private fun launchCamera() = viewModelScope.launch {
        getTakePictureUriUseCase.invoke(Unit)
            .onSuccess {
                takePictureUri.value = it
                postSideEffect { DefectCompletionSideEffect.NavigateToCamera(it) }
            }
            .onFailure {
                Log.e(tag, "launchCamera : $it")
                setDialog(DialogMessage(title = context.getString(it.handleError())))
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

    /* 에러 다이얼로그 */
    private fun setDialog(message: DialogMessage?) {
        val action = message?.let { null } ?: currentState.dialogMessage?.action
        reduce { copy(dialogMessage = message) }
        action?.let {
            when (it) {
                DialogAction.NAVIGATE_UP -> navigateUp()
                else -> {}
            }
        }
    }

    private fun navigateUp() {
        postSideEffect { DefectCompletionSideEffect.NavigateToUp }
    }

    private fun setLoading(isLoading: Boolean) {
        reduce { copy(isLoading = isLoading) }
    }

    private fun clickCompletion(id: String) = viewModelScope.launch {
        if (!isValidImage()) return@launch
        setLoading(true)
        val param = DefectCompletionParam(
            id = id,
            afterDescription = currentState.completionContent,
            afterImageUris = currentState.images.map { it.uri }
        )
        updateDefectCompletionUseCase.invoke(param).onSuccess {
            setDialog(DialogMessage(title = context.getString(R.string.defect_completion_dialog_title), action = DialogAction.NAVIGATE_UP))
        }.onFailure {
            Log.e(tag, "clickCompletion : $it")
            setDialog(DialogMessage(title = context.getString(R.string.error_create_defect_completion_failure)))
        }.also {
            setLoading(false)
        }
    }

    /* 이미지 유효성 검사 */
    private fun isValidImage(): Boolean {
        if (currentState.images.isEmpty()) {
            setDialog(DialogMessage(title = context.getString(R.string.defect_entry_image_placeholder)))
            return false
        }

        return true
    }

    /* 이미지 회전 */
    private fun rotateImage(uri: Uri) = viewModelScope.launch {
        rotateImageUseCase.invoke(uri)
            .onFailure {
                Log.e(tag, "rotateImage : $it")
            }
    }
}