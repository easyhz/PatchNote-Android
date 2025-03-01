package com.easyhz.patchnote.ui.screen.defect.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.log.Logger
import com.easyhz.patchnote.core.common.util.resource.ResourceHelper
import com.easyhz.patchnote.core.common.util.serializable.SerializableHelper
import com.easyhz.patchnote.core.model.error.DialogAction
import com.easyhz.patchnote.core.model.error.DialogMessage
import com.easyhz.patchnote.domain.usecase.defect.CreateDefectUseCase
import com.easyhz.patchnote.domain.usecase.defect.FetchDefectUseCase
import com.easyhz.patchnote.domain.usecase.image.GetDefectImagesUseCase
import com.easyhz.patchnote.domain.usecase.image.GetTakePictureUriUseCase
import com.easyhz.patchnote.domain.usecase.image.RotateImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DefectEditViewModel @Inject constructor(
    private val logger: Logger,
    savedStateHandle: SavedStateHandle,
    serializableHelper: SerializableHelper,
    private val resourceHelper: ResourceHelper,
    getTakePictureUriUseCase: GetTakePictureUriUseCase,
    rotateImageUseCase: RotateImageUseCase,
    getDefectImagesUseCase: GetDefectImagesUseCase,
    fetchDefectUseCase: FetchDefectUseCase,
    private val createDefectUseCase: CreateDefectUseCase,
): EditViewModel(
    logger = logger,
    savedStateHandle = savedStateHandle,
    serializableHelper = serializableHelper,
    resourceHelper = resourceHelper,
    getTakePictureUriUseCase = getTakePictureUriUseCase,
    rotateImageUseCase = rotateImageUseCase,
    getDefectImagesUseCase = getDefectImagesUseCase,
    fetchDefectUseCase = fetchDefectUseCase,
) {
    override val tag: String
        get() = "DefectEditViewModel"

    override fun updateDefect() {
        viewModelScope.launch {
            hideEntryDialog()
            setLoading(true)
            createDefectUseCase.invoke(getEntryParam())
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
}