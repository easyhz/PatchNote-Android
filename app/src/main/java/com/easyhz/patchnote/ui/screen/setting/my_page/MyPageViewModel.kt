package com.easyhz.patchnote.ui.screen.setting.my_page

import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.handleError
import com.easyhz.patchnote.core.designSystem.util.dialog.BasicDialogButton
import com.easyhz.patchnote.core.model.error.DialogAction
import com.easyhz.patchnote.core.model.error.DialogMessage
import com.easyhz.patchnote.core.model.setting.MyPageItem
import com.easyhz.patchnote.domain.usecase.setting.FetchUserInformationUseCase
import com.easyhz.patchnote.domain.usecase.sign.LogOutUseCase
import com.easyhz.patchnote.domain.usecase.team.LeaveTeamUseCase
import com.easyhz.patchnote.ui.screen.setting.my_page.contract.MyPageIntent
import com.easyhz.patchnote.ui.screen.setting.my_page.contract.MyPageSideEffect
import com.easyhz.patchnote.ui.screen.setting.my_page.contract.MyPageState
import com.easyhz.patchnote.ui.theme.Red
import com.easyhz.patchnote.ui.theme.SemiBold18
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fetchUserInformationUseCase: FetchUserInformationUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val leaveTeamUseCase: LeaveTeamUseCase,
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
            MyPageItem.TEAM_INVITE_CODE -> onClickTeamInviteCode()
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

    private fun onClickTeamInviteCode() {
        postSideEffect { MyPageSideEffect.CopyTeamInviteCode(currentState.userInformation.team.inviteCode) }
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
                DialogAction.CUSTOM_ACTION -> logout()
                else -> {}
            }
        }
    }

    private fun getDialogTitleAndMessageAndPositiveButton(item: MyPageItem): Triple<String, String, BasicDialogButton?> {
        val resId = when (item) {

            MyPageItem.LOGOUT -> Triple(
                R.string.logout_dialog_title,
                R.string.logout_dialog_message,
                getDefaultPositiveButton(
                    text = context.getString(R.string.logout_dialog_positive_button),
                    onClick = ::logout
                )
            )

            MyPageItem.LEAVE_TEAM -> Triple(
                R.string.leave_team_dialog_title,
                R.string.leave_team_dialog_message,
                getDefaultPositiveButton(
                    text = context.getString(R.string.leave_team_dialog_positive_button),
                    onClick = ::leaveTeam
                )
            )

            MyPageItem.WITHDRAW -> Triple(
                R.string.withdraw_dialog_title,
                R.string.withdraw_dialog_message,
                getDefaultPositiveButton(
                    text = context.getString(R.string.withdraw_dialog_positive_button),
                    onClick = ::leaveTeam
                )
            )

            else -> Triple(0, 0, null)
        }

        return Triple(context.getString(resId.first), context.getString(resId.second), resId.third)
    }

    private fun getDefaultPositiveButton(text: String, onClick: () -> Unit): BasicDialogButton {
        return BasicDialogButton(
            text = text,
            style = SemiBold18.copy(color = Color.White),
            backgroundColor = Red,
            onClick = onClick
        )
    }

    private fun setLoading(isLoading: Boolean) {
        if (currentState.isLoading == isLoading) return
        reduce { copy(isLoading = isLoading) }
    }
}