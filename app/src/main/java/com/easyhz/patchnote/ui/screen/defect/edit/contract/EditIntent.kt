package com.easyhz.patchnote.ui.screen.defect.edit.contract

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.core.designSystem.component.bottomSheet.ImageBottomSheetType
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.error.DialogMessage
import com.easyhz.patchnote.core.model.image.DefectImage

sealed class EditIntent: UiIntent() {
    data class ChangeEntryContent(val value: String): EditIntent()
    data class ChangeStateImageBottomSheet(val isShow: Boolean): EditIntent()
    data class ClickImageBottomSheet(val imageBottomSheetType: ImageBottomSheetType): EditIntent()
    data class PickImages(val images: List<Uri>) : EditIntent()
    data class TakePicture(val isUsed: Boolean) : EditIntent()
    data class DeleteImage(val image: DefectImage) : EditIntent()
    data class ClickReceipt(val entryItem: LinkedHashMap<CategoryType, TextFieldValue>, val invalidEntry: CategoryType?): EditIntent()
    data object NavigateToUp: EditIntent()
    data class SetDialog(val message: DialogMessage?): EditIntent()
    data class SetLoading(val isLoading: Boolean): EditIntent()
    data object UpdateDefect: EditIntent()
    data object HideEntryDialog: EditIntent()
}