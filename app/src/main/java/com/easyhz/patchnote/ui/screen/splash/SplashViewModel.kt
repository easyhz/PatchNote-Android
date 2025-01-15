package com.easyhz.patchnote.ui.screen.splash

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.domain.usecase.sign.IsLoginUseCase
import com.easyhz.patchnote.domain.usecase.sign.UpdateUserUseCase
import com.easyhz.patchnote.ui.screen.splash.contract.SplashIntent
import com.easyhz.patchnote.ui.screen.splash.contract.SplashSideEffect
import com.easyhz.patchnote.ui.screen.splash.contract.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel  @Inject constructor(
    private val isLoginUseCase: IsLoginUseCase,
    private val updateUserUseCase: UpdateUserUseCase
): BaseViewModel<SplashState, SplashIntent, SplashSideEffect>(
    SplashState
) {
    override fun handleIntent(intent: SplashIntent) { }

    init {
        isLogin()
    }

    private fun isLogin() = viewModelScope.launch {
        isLoginUseCase.invoke(Unit).onSuccess {
            if (it) {
                updateUser()
            }
            else { navigateToOnboarding() }
        }.onFailure { e ->
            Log.e("MainViewModel", "isLogin: ${e.message}")
            navigateToOnboarding()
        }
    }
    private fun updateUser() = viewModelScope.launch {
        updateUserUseCase.invoke(Unit).onSuccess {
            navigateToHome()
        }.onFailure {
            Log.e("MainViewModel", "updateUser: ${it.message}")
        }
    }

    private fun navigateToHome() {
        postSideEffect {  SplashSideEffect.NavigateToHome }
    }

    private fun navigateToOnboarding() {
        postSideEffect {  SplashSideEffect.NavigateToOnboarding }
    }

}