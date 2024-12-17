package com.easyhz.patchnote.ui.screen.setting.my_page.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.team.Team
import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.core.model.user.UserInformation

data class MyPageState(
    val userInformation: UserInformation,
): UiState() {
    companion object {
        fun init() = MyPageState(
            userInformation = UserInformation(
                user = User.Empty,
                team = Team.Empty
            )
        )
    }
}
