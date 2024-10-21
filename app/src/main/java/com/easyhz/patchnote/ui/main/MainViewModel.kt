package com.easyhz.patchnote.ui.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.domain.usecase.sign.IsLoginUseCase
import com.easyhz.patchnote.ui.main.contract.MainIntent
import com.easyhz.patchnote.ui.main.contract.MainSideEffect
import com.easyhz.patchnote.ui.main.contract.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isLoginUseCase: IsLoginUseCase,
): BaseViewModel<MainState, MainIntent, MainSideEffect>(
    MainState.init()
) {

    override fun handleIntent(intent: MainIntent) { }

    init {
        isLogin()
    }

    private fun isLogin() = viewModelScope.launch {
        isLoginUseCase.invoke(Unit).onSuccess {
            reduce { copy(isLogin = it) }
        }.onFailure { e ->
            Log.e("MainViewModel", "isLogin: ${e.message}")
            reduce { copy(isLogin = false) }
        }.also {
            delay(500)
            reduce { copy(isLoading = false) }
        }
    }
}