package com.easyhz.patchnote.ui.screen.defectCompletion.contract

import android.net.Uri
import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.core.designSystem.component.bottomSheet.ImageBottomSheetType
import com.easyhz.patchnote.core.model.error.DialogMessage
import com.easyhz.patchnote.core.model.image.DefectImage


sealed class DefectCompletionIntent: UiIntent() {
    data class ChangeCompletionContent(val value: String): DefectCompletionIntent()
    data class ChangeStateImageBottomSheet(val isShow: Boolean): DefectCompletionIntent()
    data class ClickImageBottomSheet(val imageBottomSheetType: ImageBottomSheetType): DefectCompletionIntent()
    data class PickImages(val images: List<Uri>) : DefectCompletionIntent()
    data class TakePicture(val isUsed: Boolean) : DefectCompletionIntent()
    data class DeleteImage(val image: DefectImage) : DefectCompletionIntent()
    data class ClickCompletion(val id: String): DefectCompletionIntent()
    data object NavigateToUp: DefectCompletionIntent()
    data class ShowError(val message: DialogMessage?): DefectCompletionIntent()
    data class SetLoading(val isLoading: Boolean): DefectCompletionIntent()
}