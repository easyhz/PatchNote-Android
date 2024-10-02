package com.easyhz.patchnote.ui.screen.defectEntry.contract

import android.net.Uri
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.text.input.TextFieldValue
import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.core.designSystem.component.bottomSheet.ImageBottomSheetType
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.image.DefectImage

sealed class DefectEntryIntent: UiIntent() {
    data class ChangeEntryValueTextValue(val categoryType: CategoryType, val value: TextFieldValue): DefectEntryIntent()
    data class ClickCategoryDropDown(val categoryType: CategoryType, val value: String): DefectEntryIntent()
    data class ChangeFocusState(val categoryType: CategoryType, val focusState: FocusState): DefectEntryIntent()
    data class ChangeEntryContent(val value: String): DefectEntryIntent()
    data class ChangeStateImageBottomSheet(val isShow: Boolean): DefectEntryIntent()
    data class ClickImageBottomSheet(val imageBottomSheetType: ImageBottomSheetType): DefectEntryIntent()
    data class PickImages(val images: List<Uri>) : DefectEntryIntent()
    data class TakePicture(val isUsed: Boolean) : DefectEntryIntent()
    data class DeleteImage(val image: DefectImage) : DefectEntryIntent()
    data object ClickReceipt: DefectEntryIntent()
}