package com.easyhz.patchnote.core.model.setting

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.model.user.UserInformation
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Red

enum class MyPageItem(
    @StringRes val titleResId: Int,
    @DrawableRes val iconResId: Int? = null,
    val textColor: Color = MainText,
    val enabledTitle: Boolean = true,
    val enabledClick: Boolean = false
) {
    NAME(
        titleResId = R.string.setting_my_page_name
    ) {
        override fun getValue(userInformation: UserInformation): String {
            return userInformation.user.name
        }
    }, PHONE(
        titleResId = R.string.setting_my_page_phone
    ) {
        override fun getValue(userInformation: UserInformation): String {
            return userInformation.user.phone
        }
    }, TEAM_NAME(
        titleResId = R.string.setting_my_page_team_name
    ) {
        override fun getValue(userInformation: UserInformation): String {
            return userInformation.team.name
        }
    }, TEAM_INVITE_CODE(
        titleResId = R.string.setting_my_page_team_invite_code,
        iconResId = R.drawable.ic_clip_board,
        enabledClick = true
    ) {
        override fun getValue(userInformation: UserInformation): String {
            return userInformation.team.inviteCode
        }
    }, LOGOUT(
        titleResId = R.string.setting_my_page_logout,
        enabledTitle = false,
        enabledClick = true
    ) {
        override fun getValue(userInformation: UserInformation): String {
            return ""
        }
    }, LEAVE_TEAM(
        titleResId = R.string.setting_my_page_leave_team,
        textColor = Red,
        enabledTitle = false,
        enabledClick = true
    ) {
        override fun getValue(userInformation: UserInformation): String {
            return ""
        }
    }, WITHDRAW(
        titleResId = R.string.setting_my_page_withdraw,
        textColor = Red,
        enabledTitle = false,
        enabledClick = true
    ) {
        override fun getValue(userInformation: UserInformation): String {
            return ""
        }
    };

    abstract fun getValue(userInformation: UserInformation): String
}