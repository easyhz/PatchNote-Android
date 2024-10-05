package com.easyhz.patchnote.ui.screen.defect

import android.content.Context
import android.util.Log
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.util.search.SearchHelper
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.category.getValue
import com.easyhz.patchnote.domain.usecase.category.FetchCategoryUseCase
import com.easyhz.patchnote.ui.screen.defect.contract.DefectIntent
import com.easyhz.patchnote.ui.screen.defect.contract.DefectSideEffect
import com.easyhz.patchnote.ui.screen.defect.contract.DefectState
import com.easyhz.patchnote.ui.screen.defect.contract.DefectState.Companion.updateEntryItemValue
import com.easyhz.patchnote.ui.screen.defect.contract.DefectState.Companion.updateSearchCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DefectViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fetchCategoryUseCase: FetchCategoryUseCase,
): BaseViewModel<DefectState, DefectIntent, DefectSideEffect>(
    initialState = DefectState.init()
) {
    private val tag = "DefectViewModel"

    override fun handleIntent(intent: DefectIntent) {
        when(intent) {
            is DefectIntent.ChangeEntryValueTextValue -> {
                onChangeEntryValueTextValue(intent.categoryType, intent.value)
            }

            is DefectIntent.ClickCategoryDropDown -> {
                onClickCategoryDropDown(intent.categoryType, intent.value)
            }

            is DefectIntent.ChangeFocusState -> {
                onChangeFocusState(intent.categoryType, intent.focusState)
            }
            is DefectIntent.ValidateEntryItem -> {
                validateEntryItem()
            }
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

    /* validation */
    private fun validateEntryItem() {
        val invalidEntry = currentState.entryItem.entries
            .firstOrNull { (category, value) -> value.text.isBlank() || !isExistCategoryList(category) }
        sendValid(invalidEntry = invalidEntry?.key)
    }

    /* 유효 여부 */
    private fun sendValid(invalidEntry: CategoryType?) {
        postSideEffect { DefectSideEffect.ValidateEntryItem(invalidEntry) }
    }
}