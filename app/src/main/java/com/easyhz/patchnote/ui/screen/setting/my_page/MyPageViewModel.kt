package com.easyhz.patchnote.ui.screen.setting.my_page

import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.model.setting.MyPageItem
import com.easyhz.patchnote.domain.usecase.setting.FetchUserInformationUseCase
import com.easyhz.patchnote.ui.screen.setting.my_page.contract.MyPageIntent
import com.easyhz.patchnote.ui.screen.setting.my_page.contract.MyPageSideEffect
import com.easyhz.patchnote.ui.screen.setting.my_page.contract.MyPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val fetchUserInformationUseCase: FetchUserInformationUseCase,
): BaseViewModel<MyPageState, MyPageIntent, MyPageSideEffect>(
    initialState = MyPageState.init(),
) {

    override fun handleIntent(intent: MyPageIntent) {
        when(intent) {
            is MyPageIntent.ClickMyPageItem -> onClickMyPageItem(intent.myPageItem)
            is MyPageIntent.NavigateToUp -> navigateUp()
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
            MyPageItem.LOGOUT, MyPageItem.LEAVE_TEAM -> logout()
            else -> { }
        }
    }
    private fun onClickTeamInviteCode() {
        postSideEffect { MyPageSideEffect.CopyTeamInviteCode(currentState.userInformation.team.inviteCode) }
    }

    private fun logout() {

    }

    private fun navigateUp() {
        postSideEffect { MyPageSideEffect.NavigateToUp }
    }
}