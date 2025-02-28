package com.easyhz.patchnote.ui.screen.defect.defectEntry.contract

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.core.designSystem.component.bottomSheet.ImageBottomSheetType
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.error.DialogMessage
import com.easyhz.patchnote.core.model.image.DefectImage

sealed class DefectEntryIntent: UiIntent() {
    data class ChangeEntryContent(val value: String): DefectEntryIntent()
    data class ChangeStateImageBottomSheet(val isShow: Boolean): DefectEntryIntent()
    data class ClickImageBottomSheet(val imageBottomSheetType: ImageBottomSheetType): DefectEntryIntent()
    data class PickImages(val images: List<Uri>) : DefectEntryIntent()
    data class TakePicture(val isUsed: Boolean) : DefectEntryIntent()
    data class DeleteImage(val image: DefectImage) : DefectEntryIntent()
    data class ClickReceipt(val entryItem: LinkedHashMap<CategoryType, TextFieldValue>, val invalidEntry: CategoryType?): DefectEntryIntent()
    data object NavigateToUp: DefectEntryIntent()
    data class SetDialog(val message: DialogMessage?): DefectEntryIntent()
    data class SetLoading(val isLoading: Boolean): DefectEntryIntent()
    data object SaveDefect: DefectEntryIntent()
    data object SaveOfflineDefect: DefectEntryIntent()
    data object HideEntryDialog: DefectEntryIntent()
}