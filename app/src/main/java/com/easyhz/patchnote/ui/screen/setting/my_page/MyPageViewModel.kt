package com.easyhz.patchnote.ui.screen.setting.my_page

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.handleError
import com.easyhz.patchnote.core.common.util.resource.ResourceHelper
import com.easyhz.patchnote.core.designSystem.util.dialog.BasicDialogButton
import com.easyhz.patchnote.core.model.error.DialogAction
import com.easyhz.patchnote.core.model.error.DialogMessage
import com.easyhz.patchnote.core.model.setting.MyPageItem
import com.easyhz.patchnote.domain.usecase.setting.FetchUserInformationUseCase
import com.easyhz.patchnote.domain.usecase.sign.LogOutUseCase
import com.easyhz.patchnote.domain.usecase.team.LeaveTeamUseCase
import com.easyhz.patchnote.domain.usecase.team.WithdrawUseCase
import com.easyhz.patchnote.ui.screen.setting.my_page.contract.MyPageIntent
import com.easyhz.patchnote.ui.screen.setting.my_page.contract.MyPageSideEffect
import com.easyhz.patchnote.ui.screen.setting.my_page.contract.MyPageState
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.Red
import com.easyhz.patchnote.ui.theme.SemiBold18
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val resourceHelper: ResourceHelper,
    private val fetchUserInformationUseCase: FetchUserInformationUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val leaveTeamUseCase: LeaveTeamUseCase,
    private val withdrawUseCase: WithdrawUseCase,
) : BaseViewModel<MyPageState, MyPageIntent, MyPageSideEffect>(
    initialState = MyPageState.init(),
) {
    private val tag = this::class.java.simpleName
    override fun handleIntent(intent: MyPageIntent) {
        when (intent) {
            is MyPageIntent.ClickMyPageItem -> onClickMyPageItem(intent.myPageItem)
            is MyPageIntent.NavigateToUp -> navigateUp()
            is MyPageIntent.ShowError -> handleDialog(intent.message)
        }
    }

    init {
        init()
    }

    private fun init() {
        fetchUserInfo()
    }

    private fun fetchUserInfo() {
        viewModelScope.launch {
            fetchUserInformationUseCase.invoke(Unit).onSuccess {
                reduce { copy(userInformation = it) }
            }
        }
    }

    private fun onClickMyPageItem(item: MyPageItem) {
        when (item) {
            MyPageItem.LOGOUT, MyPageItem.LEAVE_TEAM, MyPageItem.WITHDRAW -> setDialogDetail(item)
            else -> {}
        }
    }

    private fun setDialogDetail(item: MyPageItem) {
        val (title, message, positiveButton) = getDialogTitleAndMessageAndPositiveButton(item)

        val dialogMessage = DialogMessage(
            title = title,
            message = message,
            positiveButton = positiveButton,
        )

        handleDialog(dialogMessage)
    }

    private fun logout() {
        handleDialog(null)
        viewModelScope.launch {
            setLoading(true)
            logOutUseCase.invoke(Unit).onSuccess {
                navigateToOnboarding()
            }.onFailure { e ->
                Log.e(tag, "logout failed", e)
                showSnackBar(context, e.handleError()) {
                    MyPageSideEffect.ShowSnackBar(it)
                }
            }.also {
                setLoading(false)
            }
        }
    }

    private fun leaveTeam() {
        handleDialog(null)
        viewModelScope.launch {
            val uid = currentState.userInformation.user.id
            setLoading(true)
            leaveTeamUseCase.invoke(uid).onSuccess {
                navigateToOnboarding()
            }.onFailure { e ->
                Log.e(tag, "leave team failed", e)
                showSnackBar(context, e.handleError()) {
                    MyPageSideEffect.ShowSnackBar(it)
                }
            }.also {
                setLoading(false)
            }
        }
    }

    private fun withdraw() {
        handleDialog(null)
        viewModelScope.launch {
            val uid = currentState.userInformation.user.id
            setLoading(true)
            withdrawUseCase.invoke(uid).onSuccess {
                navigateToOnboarding()
            }.onFailure { e ->
                Log.e(tag, "withdraw failed", e)
                showSnackBar(context, e.handleError()) {
                    MyPageSideEffect.ShowSnackBar(it)
                }
            }.also {
                setLoading(false)
            }
        }
    }

    private fun navigateUp() {
        postSideEffect { MyPageSideEffect.NavigateToUp }
    }

    private fun navigateToOnboarding() {
        postSideEffect { MyPageSideEffect.NavigateToOnboarding }
    }

    private fun handleDialog(message: DialogMessage?) {
        val action = message?.let { null } ?: currentState.dialogMessage?.action
        reduce { copy(dialogMessage = message) }
        action?.let {
            when (it) {
                DialogAction.CustomAction -> logout()
                else -> {}
            }
        }
    }

    private fun getDialogTitleAndMessageAndPositiveButton(item: MyPageItem): Triple<String, String, BasicDialogButton?> {
        val resId = when (item) {
            MyPageItem.LOGOUT -> Triple(
                resourceHelper.getString(R.string.logout_dialog_title),
                resourceHelper.getString(R.string.logout_dialog_message),
                getDefaultPositiveButton(
                    text = resourceHelper.getString(R.string.logout_dialog_positive_button),
                    onClick = ::logout
                )
            )

            MyPageItem.LEAVE_TEAM -> Triple(
                resourceHelper.getString(R.string.leave_team_dialog_title),
                resourceHelper.getString(R.string.leave_team_dialog_message, currentState.userInformation.team.name),
                getDefaultPositiveButton(
                    text = resourceHelper.getString(R.string.leave_team_dialog_positive_button),
                    onClick = ::leaveTeam
                )
            )

            MyPageItem.WITHDRAW -> Triple(
                resourceHelper.getString(R.string.withdraw_dialog_title),
                resourceHelper.getString(R.string.withdraw_dialog_message),
                getDefaultPositiveButton(
                    text = resourceHelper.getString(R.string.withdraw_dialog_positive_button),
                    onClick = ::withdraw
                )
            )

            else -> Triple("", "", null)
        }

        return resId
    }

    private fun getDefaultPositiveButton(text: String, onClick: () -> Unit): BasicDialogButton {
        return BasicDialogButton(
            text = text,
            style = SemiBold18.copy(color = MainBackground),
            backgroundColor = Red,
            onClick = onClick
        )
    }

    private fun setLoading(isLoading: Boolean) {
        if (currentState.isLoading == isLoading) return
        reduce { copy(isLoading = isLoading) }
    }
}