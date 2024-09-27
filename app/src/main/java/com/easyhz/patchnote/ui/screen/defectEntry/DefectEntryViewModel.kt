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

    /* fetchCategory */
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

    /* [CategoryType] 의 텍스트 필드 값 변경 */
    private fun onChangeEntryValueTextValue(categoryType: CategoryType, value: TextFieldValue) {
        val filterValue = when(categoryType) {
            CategoryType.BUILDING, CategoryType.UNIT -> TextFieldValue(value.text.filter { it.isDigit() })
            else -> value
        }
        reduce { updateEntryItemValue(categoryType, filterValue) }
        searchCategory(categoryType, value.text)
    }

    /* 드롭다운 항목 클릭 */
    private fun onClickCategoryDropDown(categoryType: CategoryType, value: String) {
        reduce { updateEntryItemValue(categoryType, TextFieldValue(value), isSelected = true) }
    }

    /* 카테고리 자동 완성 검색 */
    private fun searchCategory(categoryType: CategoryType, value: String) {
        val items = currentState.category.getValue(categoryType)?.values ?: emptyList()
        val searchResult = SearchHelper.search(value, items)
        reduce { updateSearchCategory(categoryType, searchResult) }
    }

    /* 포커스 해지 되면 등록된 카테고리에 있는지 확인 후 없으면 빈칸으로 리셋 */
    private fun onChangeFocusState(categoryType: CategoryType, focusState: FocusState) {
        if (focusState.isFocused) return
        if (categoryType == CategoryType.BUILDING || categoryType == CategoryType.UNIT) return
        val isExistCategoryList = currentState.category.getValue(categoryType)?.values?.contains(currentState.entryItem[categoryType]?.text)
        if (isExistCategoryList == true) return
        reduce { updateEntryItemValue(categoryType, TextFieldValue("")) }
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
                println(">> 실패 : $it")
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
}