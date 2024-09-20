package com.easyhz.patchnote.ui.screen.dataManagement

import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.domain.usecase.category.FetchCategoryUseCase
import com.easyhz.patchnote.ui.screen.dataManagement.contract.DataIntent
import com.easyhz.patchnote.ui.screen.dataManagement.contract.DataSideEffect
import com.easyhz.patchnote.ui.screen.dataManagement.contract.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataManagementViewModel @Inject constructor(
    private val fetchCategoryUseCase: FetchCategoryUseCase
): BaseViewModel<DataState, DataIntent, DataSideEffect>(
    initialState = DataState.init()
) {
    override fun handleIntent(intent: DataIntent) {
        TODO("Not yet implemented")
    }

    init {
        fetchCategory()
    }

    private fun fetchCategory() = viewModelScope.launch {
        fetchCategoryUseCase.invoke(Unit).onSuccess {
            reduce { copy(category = it) }
        }.onFailure {
            println("실패 >>> $it")
        }
    }
}