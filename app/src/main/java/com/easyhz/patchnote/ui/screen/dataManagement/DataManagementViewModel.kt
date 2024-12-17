package com.easyhz.patchnote.ui.screen.dataManagement

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.model.category.DeleteCategory
import com.easyhz.patchnote.domain.usecase.category.DeleteCategoryUseCase
import com.easyhz.patchnote.domain.usecase.category.FetchCategoryUseCase
import com.easyhz.patchnote.ui.screen.dataManagement.contract.DataIntent
import com.easyhz.patchnote.ui.screen.dataManagement.contract.DataSideEffect
import com.easyhz.patchnote.ui.screen.dataManagement.contract.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataManagementViewModel @Inject constructor(
    private val fetchCategoryUseCase: FetchCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
) : BaseViewModel<DataState, DataIntent, DataSideEffect>(
    initialState = DataState.init()
) {
    private val tag = "DataManagementViewModel"
    override fun handleIntent(intent: DataIntent) {
        when (intent) {
            is DataIntent.DeleteCategoryValue -> { deleteCategoryValue(intent.categoryIndex, intent.index) }
            is DataIntent.NavigateToDataEntry -> { navigateToDataEntry() }
            is DataIntent.NavigateToUp -> { navigateToUp() }
            is DataIntent.HideDeleteDialog -> { hideDeleteDialog() }
            is DataIntent.ClickPositiveButton -> { deleteCategory() }
            is DataIntent.OnResume -> { fetchCategory() }
        }
    }

    init {
        fetchCategory()
    }

    private fun fetchCategory() = viewModelScope.launch {
        fetchCategoryUseCase.invoke(Unit).onSuccess {
            reduce { copy(category = it) }
        }.onFailure {
            Log.e(tag, "fetchCategory : ${it.message}", it)
        }
    }

    private fun deleteCategory() = viewModelScope.launch {
        val category = currentState.deleteCategory
        val index = currentState.deleteCategoryItemIndex
        if (category.isNullOrBlank() || index == null) return@launch
        val param = DeleteCategory(
            category = category,
            index = index
        )

        deleteCategoryUseCase.invoke(param).onSuccess {
            println(">> 성공")
        }.onFailure {
            Log.e(tag, "fetchCategory : ${it.message}", it)
        }.also {
            hideDeleteDialog()
            fetchCategory()
        }
    }

    private fun deleteCategoryValue(categoryIndex: Int, index: Int) {
        val deleteCategory = currentState.category[categoryIndex]
        val deleteItem = deleteCategory.values[index]
        reduce {
            copy(
                isShowDeleteDialog = true,
                deleteItem = deleteItem,
                deleteCategory = deleteCategory.type.alias,
                deleteCategoryItemIndex = index
            )
        }
    }

    private fun navigateToDataEntry() {
        postSideEffect { DataSideEffect.NavigateToDataEntry }
    }

    private fun navigateToUp() {
        postSideEffect { DataSideEffect.NavigateToUp }
    }

    private fun hideDeleteDialog() {
        reduce {
            copy(
                isShowDeleteDialog = false,
                deleteItem = "",
                deleteCategory = null,
                deleteCategoryItemIndex = null
            )
        }
    }
}