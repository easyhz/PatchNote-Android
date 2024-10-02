package com.easyhz.patchnote.ui.screen.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.domain.usecase.defect.FetchDefectsUseCase
import com.easyhz.patchnote.ui.screen.home.contract.HomeIntent
import com.easyhz.patchnote.ui.screen.home.contract.HomeSideEffect
import com.easyhz.patchnote.ui.screen.home.contract.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchDefectsUseCase: FetchDefectsUseCase,
): BaseViewModel<HomeState, HomeIntent, HomeSideEffect>(
    initialState = HomeState.init()
) {
    private val tag = "HomeViewModel"

    override fun handleIntent(intent: HomeIntent) {
        TODO("Not yet implemented")
    }

    init {
        fetchDefects()
    }

    /* fetchDefects */
    private fun fetchDefects() = viewModelScope.launch {
        fetchDefectsUseCase.invoke(Unit).onSuccess {
            reduce { copy(defectList = it) }
        }.onFailure {
            Log.e(tag, "fetchCategory : $it")
        }
    }
}