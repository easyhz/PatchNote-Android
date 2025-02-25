package com.easyhz.patchnote.ui.screen.splash

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.util.version.Version
import com.easyhz.patchnote.domain.usecase.configuration.FetchConfigurationUseCase
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
    private val updateUserUseCase: UpdateUserUseCase,
    private val fetchConfigurationUseCase: FetchConfigurationUseCase,
): BaseViewModel<SplashState, SplashIntent, SplashSideEffect>(
    SplashState.init()
) {
    private val tag = "SplashViewModel"

    override fun handleIntent(intent: SplashIntent) {
        when (intent) {
            is SplashIntent.UpdateAppVersion -> updateAppVersion()
        }
    }

    init {
        fetchConfiguration()
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

    /* fetchConfiguration */
    private fun fetchConfiguration() = viewModelScope.launch {
        fetchConfigurationUseCase.invoke(Unit).onSuccess {
            val needsUpdate = Version.needsUpdate(it.androidVersion)
            reduce { copy(needsUpdate = needsUpdate, appConfiguration = it) }
            if (needsUpdate) return@launch
            isLogin()
        }.onFailure {
            Log.e(tag, "fetchConfiguration : $it")
        }
    }

    private fun navigateToHome() {
        postSideEffect { SplashSideEffect.NavigateToHome }
    }

    private fun navigateToOnboarding() {
        postSideEffect { SplashSideEffect.NavigateToOnboarding }
    }

    /* updateAppVersion */
    private fun updateAppVersion() {
        postSideEffect { SplashSideEffect.NavigateToUrl("https://play.google.com/store/apps/details?id=com.easyhz.patchnote") }
    }

}