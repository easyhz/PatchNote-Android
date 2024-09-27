package com.easyhz.patchnote.ui.screen.defectEntry

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.util.search.SearchHelper
import com.easyhz.patchnote.core.designSystem.component.bottomSheet.ImageBottomSheetType
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.category.getValue
import com.easyhz.patchnote.core.model.image.DefectImage
import com.easyhz.patchnote.core.model.image.toDefectImages
import com.easyhz.patchnote.domain.usecase.category.FetchCategoryUseCase
import com.easyhz.patchnote.domain.usecase.image.GetTakePictureUriUseCase
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntryIntent
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntrySideEffect
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntryState
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntryState.Companion.deleteImage
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntryState.Companion.updateEntryItemValue
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntryState.Companion.updateImages
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntryState.Companion.updateSearchCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DefectEntryViewModel @Inject constructor(
    private val fetchCategoryUseCase: FetchCategoryUseCase,
    private val getTakePictureUriUseCase: GetTakePictureUriUseCase
): BaseViewModel<DefectEntryState, DefectEntryIntent, DefectEntrySideEffect>(
    initialState = DefectEntryState.init()
) {
    private var takePictureUri = mutableStateOf(Uri.EMPTY)
    override fun handleIntent(intent: DefectEntryIntent) {
        when(intent) {
            is DefectEntryIntent.ChangeEntryValueTextValue -> { onChangeEntryValueTextValue(intent.categoryType, intent.value) }
            is DefectEntryIntent.ClickCategoryDropDown -> { onClickCategoryDropDown(intent.categoryType, intent.value) }
            is DefectEntryIntent.ChangeFocusState -> { onChangeFocusState(intent.categoryType, intent.focusState) }
            is DefectEntryIntent.ChangeEntryContent -> { reduce { copy(entryContent = intent.value) } }
            is DefectEntryIntent.ChangeStateImageBottomSheet -> { changeStateImageBottomSheet(intent.isShow) }
            is DefectEntryIntent.ClickImageBottomSheet -> { onClickImageBottomSheet(intent.imageBottomSheetType) }
            is DefectEntryIntent.PickImages -> { updateEntryImages(intent.images) }
            is DefectEntryIntent.TakePicture -> { updateTakePicture(intent.isUsed) }
            is DefectEntryIntent.DeleteImage -> { deleteEntryImage(intent.image) }
        }
    }

    init {
        fetchCategory()
    }

    private fun fetchCategory() = viewModelScope.launch {
        fetchCategoryUseCase.invoke(Unit).onSuccess {
            reduce { copy(category = it) }
            currentState.category.forEach {
                searchCategory(it.type, "")
            }
        }.onFailure {
            println(">> 실패 : $it")
        }
    }

    private fun onChangeEntryValueTextValue(categoryType: CategoryType, value: TextFieldValue) {
        val filterValue = when(categoryType) {
            CategoryType.BUILDING, CategoryType.UNIT -> TextFieldValue(value.text.filter { it.isDigit() })
            else -> value
        }
        reduce { updateEntryItemValue(categoryType, filterValue) }
        searchCategory(categoryType, value.text)
    }

    private fun onClickCategoryDropDown(categoryType: CategoryType, value: String) {
        reduce { updateEntryItemValue(categoryType, TextFieldValue(value), isSelected = true) }
    }

    private fun searchCategory(categoryType: CategoryType, value: String) {
        val items = currentState.category.getValue(categoryType)?.values ?: emptyList()
        val searchResult = SearchHelper.search(value, items)
        reduce { updateSearchCategory(categoryType, searchResult) }
    }

    private fun onChangeFocusState(categoryType: CategoryType, focusState: FocusState) {
        if (focusState.isFocused) return
        if (categoryType == CategoryType.BUILDING || categoryType == CategoryType.UNIT) return
        val isExistCategoryList = currentState.category.getValue(categoryType)?.values?.contains(currentState.entryItem[categoryType]?.text)
        if (isExistCategoryList == true) return
        reduce { updateEntryItemValue(categoryType, TextFieldValue("")) }
    }

    private fun changeStateImageBottomSheet(isShow: Boolean) {
        reduce { copy(isShowImageBottomSheet = isShow) }
    }

    private fun onClickImageBottomSheet(imageBottomSheetType: ImageBottomSheetType) {
        when(imageBottomSheetType) {
            ImageBottomSheetType.GALLERY -> { launchGallery() }
            ImageBottomSheetType.CAMERA -> { launchCamera() }
        }
    }

    private fun launchGallery() {
        postSideEffect { DefectEntrySideEffect.NavigateToGallery }
    }

    private fun launchCamera() = viewModelScope.launch {
        getTakePictureUriUseCase.invoke(Unit)
            .onSuccess {
                takePictureUri.value = it
                postSideEffect { DefectEntrySideEffect.NavigateToCamera(it) }
            }
            .onFailure {
                println(">> 실패 : $it")
            }
    }

    private fun updateEntryImages(newImages: List<Uri>) {
        reduce { updateImages(newImages.toDefectImages()) }
    }

    private fun updateTakePicture(isUsed: Boolean) {
        if (!isUsed) return
        reduce { updateImages(newImages = listOf(takePictureUri.value).toDefectImages()) }
    }

    private fun deleteEntryImage(image: DefectImage) {
        reduce { deleteImage(image) }
    }
}