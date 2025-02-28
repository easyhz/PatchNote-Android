package com.easyhz.patchnote.ui.screen.defect.edit.contract

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.core.designSystem.component.bottomSheet.ImageBottomSheetType
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.error.DialogMessage
import com.easyhz.patchnote.core.model.image.DefectImage

sealed class DefectEditIntent: UiIntent() {
    data class ChangeEntryContent(val value: String): DefectEditIntent()
    data class ChangeStateImageBottomSheet(val isShow: Boolean): DefectEditIntent()
    data class ClickImageBottomSheet(val imageBottomSheetType: ImageBottomSheetType): DefectEditIntent()
    data class PickImages(val images: List<Uri>) : DefectEditIntent()
    data class TakePicture(val isUsed: Boolean) : DefectEditIntent()
    data class DeleteImage(val image: DefectImage) : DefectEditIntent()
    data class ClickReceipt(val entryItem: LinkedHashMap<CategoryType, TextFieldValue>, val invalidEntry: CategoryType?): DefectEditIntent()
    data object NavigateToUp: DefectEditIntent()
    data class SetDialog(val message: DialogMessage?): DefectEditIntent()
    data class SetLoading(val isLoading: Boolean): DefectEditIntent()
    data object UpdateDefect: DefectEditIntent()
    data object HideEntryDialog: DefectEditIntent()
}