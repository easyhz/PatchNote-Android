package com.easyhz.patchnote.ui.screen.defectEntry

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.handleError
import com.easyhz.patchnote.core.common.util.Generate
import com.easyhz.patchnote.core.common.util.getPostposition
import com.easyhz.patchnote.core.common.util.search.SearchHelper
import com.easyhz.patchnote.core.designSystem.component.bottomSheet.ImageBottomSheetType
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.category.getValue
import com.easyhz.patchnote.core.model.defect.EntryDefectParam
import com.easyhz.patchnote.core.model.image.DefectImage
import com.easyhz.patchnote.core.model.image.toDefectImages
import com.easyhz.patchnote.domain.usecase.category.FetchCategoryUseCase
import com.easyhz.patchnote.domain.usecase.defect.CreateDefectUseCase
import com.easyhz.patchnote.domain.usecase.image.GetTakePictureUriUseCase
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntryIntent
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntrySideEffect
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntryState
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntryState.Companion.deleteImage
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntryState.Companion.updateEntryItemValue
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntryState.Companion.updateImages
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntryState.Companion.updateSearchCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DefectEntryViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fetchCategoryUseCase: FetchCategoryUseCase,
    private val getTakePictureUriUseCase: GetTakePictureUriUseCase,
    private val createDefectUseCase: CreateDefectUseCase
): BaseViewModel<DefectEntryState, DefectEntryIntent, DefectEntrySideEffect>(
    initialState = DefectEntryState.init()
) {
    private val tag = "DefectEntryViewModel"
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
            is DefectEntryIntent.ClickReceipt -> { createDefect() }
            is DefectEntryIntent.NavigateToUp -> { navigateUp() }
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
            Log.e(tag, "fetchCategory : $it")
            showSnackBar(context, it.handleError()) { value ->
                DefectEntrySideEffect.ShowSnackBar(value)
            }
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
        if (isExistCategoryList(categoryType)) return
        reduce { updateEntryItemValue(categoryType, TextFieldValue("")) }
    }

    /* 카테고리에 있는지 확인 */
    private fun isExistCategoryList(categoryType: CategoryType): Boolean {
        if (categoryType == CategoryType.BUILDING || categoryType == CategoryType.UNIT) return true
        return currentState.category.getValue(categoryType)?.values?.contains(currentState.entryItem[categoryType]?.text) == true
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
                Log.e(tag, "launchCamera : $it")
                showSnackBar(context, it.handleError()) { value ->
                    DefectEntrySideEffect.ShowSnackBar(value)
                }
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

    /* 하자 등록 */
    private fun createDefect() = viewModelScope.launch {
        if (!isValidDefect()) return@launch
        val param = EntryDefectParam(
            id = Generate.randomUUID(),
            site = currentState.entryItem[CategoryType.SITE]?.text.orEmpty(),
            building = currentState.entryItem[CategoryType.BUILDING]?.text.orEmpty(),
            unit = currentState.entryItem[CategoryType.UNIT]?.text.orEmpty(),
            space = currentState.entryItem[CategoryType.SPACE]?.text.orEmpty(),
            part = currentState.entryItem[CategoryType.PART]?.text.orEmpty(),
            workType = currentState.entryItem[CategoryType.WORK_TYPE]?.text.orEmpty(),
            beforeDescription = currentState.entryContent,
            beforeImageUris = currentState.images.map { it.uri }
        )
        createDefectUseCase.invoke(param)
            .onSuccess {
                navigateUp()
            }
            .onFailure {
                Log.e(tag, "createDefect : $it")
                showSnackBar(context, it.handleError()) { value ->
                    DefectEntrySideEffect.ShowSnackBar(value)
                }
            }
    }

    /* 하자 등록 유효성 검사 */
    private fun isValidDefect(): Boolean {
        val invalidEntry = currentState.entryItem.entries
            .firstOrNull { (type, value) -> value.text.isBlank() || !isExistCategoryList(type) }
        invalidEntry?.let { (type, _) ->
            val valueString = context.getString(type.nameId) + getPostposition(type)
            showSnackBar(
                context = context,
                value = R.string.category_empty,
                valueString
            ) { DefectEntrySideEffect.ShowSnackBar(it) }
            return false
        }

        return true
    }

    /* 뒤로가기 */
    private fun navigateUp() {
        postSideEffect { DefectEntrySideEffect.NavigateToUp }
    }
}