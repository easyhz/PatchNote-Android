package com.easyhz.patchnote.ui.screen.defect

import android.content.Context
import android.util.Log
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.AppError
import com.easyhz.patchnote.core.common.error.handleError
import com.easyhz.patchnote.core.common.util.search.SearchHelper
import com.easyhz.patchnote.core.common.util.toLinkedHashMap
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.category.getValue
import com.easyhz.patchnote.core.model.error.DialogAction
import com.easyhz.patchnote.core.model.error.DialogMessage
import com.easyhz.patchnote.core.model.filter.FilterParam
import com.easyhz.patchnote.domain.usecase.category.FetchCategoryUseCase
import com.easyhz.patchnote.domain.usecase.reception.GetReceptionSettingUseCase
import com.easyhz.patchnote.ui.screen.defect.contract.DefectIntent
import com.easyhz.patchnote.ui.screen.defect.contract.DefectSideEffect
import com.easyhz.patchnote.ui.screen.defect.contract.DefectState
import com.easyhz.patchnote.ui.screen.defect.contract.DefectState.Companion.clearEntryItemValue
import com.easyhz.patchnote.ui.screen.defect.contract.DefectState.Companion.resetData
import com.easyhz.patchnote.ui.screen.defect.contract.DefectState.Companion.updateEntryItemValue
import com.easyhz.patchnote.ui.screen.defect.contract.DefectState.Companion.updateSearchCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DefectViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fetchCategoryUseCase: FetchCategoryUseCase,
    private val getReceptionSettingUseCase: GetReceptionSettingUseCase,
) : BaseViewModel<DefectState, DefectIntent, DefectSideEffect>(
    initialState = DefectState.init()
) {
    private val tag = "DefectViewModel"

    private var categoryType = LinkedHashMap<CategoryType, Boolean>()

    override fun handleIntent(intent: DefectIntent) {
        when (intent) {
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

            is DefectIntent.ClearAllData -> {
                clearAllData()
            }

            is DefectIntent.ClearData -> {
                clearData(intent.categoryType)
            }

            is DefectIntent.SearchItem -> {
                searchItem()
            }

            is DefectIntent.InitFilter -> {
                initFilter(intent.filterParam)
            }

            is DefectIntent.Reset -> {
                reset()
            }
        }
    }

    init {
        fetchCategory()
        getReceptionSetting()
    }

    /* fetchCategory */
    private fun fetchCategory() = viewModelScope.launch {
        fetchCategoryUseCase.invoke(Unit)
            .mapCatching { result ->
                if (result.any { it.values.isEmpty() }) throw AppError.NoResultError
                result
            }.onSuccess {
                reduce { copy(category = it) }
                currentState.category.forEach {
                    searchCategory(it.type, "")
                }
            }.onFailure {
                handleErrorFetchCategory(it)
            }.also {
                sendLoadingState()
            }
    }

    private fun getReceptionSetting() = viewModelScope.launch {
        getReceptionSettingUseCase.invoke().collect {
            categoryType = it
        }
    }

    private suspend fun handleErrorFetchCategory(error: Throwable) {
        Log.e(tag, "fetchCategory : $error")
        delay(700)
        val errorMessage = when(error) {
            is AppError.NoResultError -> R.string.error_fetch_category_message_no_data
            else -> error.handleError()
        }
        val message = DialogMessage(
            title = context.getString(R.string.error_fetch_category_title),
            message = context.getString(errorMessage),
            action = DialogAction.NAVIGATE_UP
        )
        sendError(message = message)
    }

    /* [CategoryType] 의 텍스트 필드 값 변경 */
    private fun onChangeEntryValueTextValue(categoryType: CategoryType, value: TextFieldValue) {
        val filterValue = when (categoryType) {
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
            .firstOrNull { (category, value) ->
                value.text.isBlank() || !isExistCategoryList(
                    category
                )
            }
        sendValid(invalidEntry = invalidEntry?.key)
    }

    /* 유효 여부 */
    private fun sendValid(invalidEntry: CategoryType?) {
        postSideEffect { DefectSideEffect.ValidateEntryItem(invalidEntry) }
    }

    /* error */
    private fun sendError(message: DialogMessage) {
        postSideEffect { DefectSideEffect.SendError(message) }
    }

    /* loading */
    private fun sendLoadingState() {
        postSideEffect { DefectSideEffect.SendLoading(false) }
    }

    /* clearData */
    private fun clearAllData() {
        reduce { clearEntryItemValue(categoryType) }
    }

    /* 특정 아이템만 클리어 */
    private fun clearData(categoryType: CategoryType) {
        reduce { updateEntryItemValue(categoryType, TextFieldValue("")) }
    }

    /* 검색 */
    private fun searchItem() {
        val search = currentState.entryItem.entries.filter { it.value.text.isNotBlank() }
            .associate { it.key.alias to it.value.text }.toLinkedHashMap()
        postSideEffect { DefectSideEffect.SearchItem(item = search) }
    }

    /* 필드 초기설정 */
    private fun initFilter(filterParam: FilterParam) {
        val item = CategoryType.entries.associateWith {
            TextFieldValue(
                filterParam.searchFieldParam[it.alias] ?: ""
            )
        }.toLinkedHashMap()
        reduce { copy(entryItem = item) }
    }

    /* reset */
    private fun reset() {
        reduce { resetData() }
    }
}