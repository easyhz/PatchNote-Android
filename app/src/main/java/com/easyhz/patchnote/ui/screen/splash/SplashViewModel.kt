package com.easyhz.patchnote.ui.screen.splash

import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.BuildConfig
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.AppError
import com.easyhz.patchnote.core.common.util.log.Logger
import com.easyhz.patchnote.core.common.util.resource.ResourceHelper
import com.easyhz.patchnote.core.designSystem.util.dialog.BasicDialogButton
import com.easyhz.patchnote.core.model.app.AppStep
import com.easyhz.patchnote.core.model.error.DialogAction
import com.easyhz.patchnote.core.model.error.DialogMessage
import com.easyhz.patchnote.domain.usecase.app.CheckAppStepUseCase
import com.easyhz.patchnote.ui.screen.splash.contract.SplashIntent
import com.easyhz.patchnote.ui.screen.splash.contract.SplashSideEffect
import com.easyhz.patchnote.ui.screen.splash.contract.SplashState
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.Primary
import com.easyhz.patchnote.ui.theme.SemiBold18
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel  @Inject constructor(
    private val logger: Logger,
    private val resourceHelper: ResourceHelper,
    private val checkAppStepUseCase: CheckAppStepUseCase,
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
        checkAppStep()
    }

    private fun checkAppStep() {
        viewModelScope.launch {
            checkAppStepUseCase.invoke(Unit).onSuccess {
                when (it) {
                    is AppStep.Update -> handleUpdate(it.appVersion)
                    is AppStep.Maintenance -> handleMaintenance(it.message)
                    is AppStep.Home -> navigateToHome()
                    is AppStep.Onboarding -> navigateToOnboarding()
                    is AppStep.Team -> { }
                }
            }.onFailure { e ->
                logger.e(tag, "checkAppStep: ${e.message}")
                handleError(e)
            }
        }
    }

    private fun handleUpdate(version: String) {
        setDialog(
            message = DialogMessage(
                title = resourceHelper.getString(R.string.version_dialog_title),
                message = resourceHelper.getString(R.string.version_dialog_message, version, BuildConfig.VERSION_NAME),
                positiveButton = getDefaultPositiveButton(
                    text = resourceHelper.getString(R.string.version_dialog_button),
                    onClick = ::updateAppVersion
                )
            )
        )
    }

    private fun handleMaintenance(message: String) {
        setDialog(
            message = DialogMessage(
                title = message,
                action = DialogAction.NavigateUp,
                positiveButton = getDefaultPositiveButton(
                    text = resourceHelper.getString(R.string.maintenance_dialog_button),
                    onClick = ::navigateUp
                )
            )
        )
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

    private fun navigateUp() {
        postSideEffect { SplashSideEffect.NavigateUp }
    }

    /* 다이얼로그 */
    private fun setDialog(message: DialogMessage?) {
        reduce { copy(dialogMessage = message) }
    }

    private fun getDefaultPositiveButton(text: String, onClick: () -> Unit): BasicDialogButton {
        return BasicDialogButton(
            text = text,
            style = SemiBold18.copy(color = MainBackground),
            backgroundColor = Primary,
            onClick = onClick
        )
    }

    private fun handleError(e: Throwable) {
        when(e) {
            is AppError.DefaultError -> { navigateToOnboarding() }
            else -> {
                setDialog(
                    message = DialogMessage(
                        title = resourceHelper.getString(R.string.error_dialog_title),
                        message = resourceHelper.getString(R.string.error_dialog_message),
                        positiveButton = BasicDialogButton(
                            text = resourceHelper.getString(R.string.error_dialog_button),
                            style = SemiBold18.copy(color = MainBackground),
                            backgroundColor = Primary,
                            onClick = ::checkAppStep
                        )
                    )
                )
            }
        }

    }

}