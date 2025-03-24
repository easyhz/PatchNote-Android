package com.easyhz.patchnote.ui.screen.defect.offline.edit

import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.Generate
import com.easyhz.patchnote.core.common.util.log.Logger
import com.easyhz.patchnote.core.common.util.resource.ResourceHelper
import com.easyhz.patchnote.core.common.util.serializable.SerializableHelper
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.error.DialogAction
import com.easyhz.patchnote.core.model.error.DialogMessage
import com.easyhz.patchnote.core.model.image.DefectImage
import com.easyhz.patchnote.domain.usecase.defect.offline.FetchOfflineDefectUseCase
import com.easyhz.patchnote.domain.usecase.defect.offline.UpdateOfflineDefectUseCase
import com.easyhz.patchnote.domain.usecase.image.GetDefectImagesUseCase
import com.easyhz.patchnote.domain.usecase.image.GetTakePictureUriUseCase
import com.easyhz.patchnote.domain.usecase.image.RotateImageUseCase
import com.easyhz.patchnote.ui.screen.defect.edit.EditViewModel
import com.easyhz.patchnote.ui.screen.defect.edit.contract.EditSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfflineDefectEditViewModel @Inject constructor(
    private val logger: Logger,
    savedStateHandle: SavedStateHandle,
    serializableHelper: SerializableHelper,
    private val resourceHelper: ResourceHelper,
    getTakePictureUriUseCase: GetTakePictureUriUseCase,
    rotateImageUseCase: RotateImageUseCase,
    getDefectImagesUseCase: GetDefectImagesUseCase,
    private val fetchOfflineDefectUseCase: FetchOfflineDefectUseCase,
    private val updateOfflineDefectUseCase: UpdateOfflineDefectUseCase,
): EditViewModel(
    logger = logger,
    savedStateHandle = savedStateHandle,
    serializableHelper = serializableHelper,
    resourceHelper = resourceHelper,
    getTakePictureUriUseCase = getTakePictureUriUseCase,
    rotateImageUseCase = rotateImageUseCase,
    getDefectImagesUseCase = getDefectImagesUseCase,
) {
    override val tag: String
        get() = "OfflineDefectEditViewModel"

    override fun setUp(defectItem: DefectItem) {
        viewModelScope.launch {
            setLoading(true)
            val entryItem = linkedMapOf(
                CategoryType.SITE to TextFieldValue(defectItem.site),
                CategoryType.BUILDING to TextFieldValue(defectItem.building),
                CategoryType.UNIT to TextFieldValue(defectItem.unit),
                CategoryType.SPACE to TextFieldValue(defectItem.space),
                CategoryType.PART to TextFieldValue(defectItem.part),
                CategoryType.WORK_TYPE to TextFieldValue(defectItem.workType),
            )
            val defectImages = defectItem.beforeImageUrls.map {
                DefectImage(id = Generate.randomUuid(), uri = it.toUri())
            }
            reduce { copy(images = defectImages, isSuccessGetData = true) }
            delay(500)
            postSideEffect { EditSideEffect.SendEntryItem(entryItem) }
            setLoading(false)
        }
    }

    override fun updateDefect() {
        viewModelScope.launch {
            hideEntryDialog()
            setLoading(true)
            updateOfflineDefectUseCase.invoke(getEntryParam())
                .onSuccess {
                    setDialog(
                        DialogMessage(
                            title = resourceHelper.getString(R.string.defect_edit_success_dialog_title),
                            action = DialogAction.CustomAction
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

    override suspend fun fetchDefect(
        defectId: String,
        onSuccess: (DefectItem) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        fetchOfflineDefectUseCase.invoke(defectId)
            .onSuccess {
                println("fetchDefect: $it")
                onSuccess(it)
            }
            .onFailure { onFailure(it) }
    }
}