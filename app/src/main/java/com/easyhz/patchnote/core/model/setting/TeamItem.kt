package com.easyhz.patchnote.core.model.setting

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.toDateString
import com.easyhz.patchnote.core.model.team.TeamInformation
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Red

enum class TeamItem(
    @StringRes val titleResId: Int,
    @DrawableRes val iconResId: Int? = null,
    val textColor: Color = MainText,
    val enabledTitle: Boolean = true,
    val enabledClick: Boolean = false
) {
    NAME(
        titleResId = R.string.setting_team_name
    ) {
        override fun getValue(teamInformation: TeamInformation): String {
            return teamInformation.team.name
        }
    }, CREATION_DATE(
        titleResId = R.string.setting_team_creation_date
    ) {
        override fun getValue(teamInformation: TeamInformation): String {
            return teamInformation.team.creationTime.toDateString()
        }
    }, INVITE_CODE(
        titleResId = R.string.setting_team_invite_code,
        iconResId = R.drawable.ic_clip_board,
        enabledClick = true
    ) {
        override fun getValue(teamInformation: TeamInformation): String {
            return teamInformation.team.inviteCode
        }
    }, ADMIN(
        titleResId = R.string.setting_team_admin
    ) {
        override fun getValue(teamInformation: TeamInformation): String {
            return teamInformation.adminName
        }
    }, DATA_MANAGEMENT(
        titleResId = R.string.setting_team_data_management,
        iconResId = R.drawable.ic_right_arrow,
        enabledTitle = false,
        enabledClick = true
    ) {
        override fun getValue(teamInformation: TeamInformation): String {
            return ""
        }
    }, MEMBER(
        titleResId = R.string.setting_team_member,
        iconResId = R.drawable.ic_right_arrow,
        enabledTitle = false,
        enabledClick = true
    ) {
        override fun getValue(teamInformation: TeamInformation): String {
            return ""
        }
    }, LEAVE(
        titleResId = R.string.setting_team_leave,
        textColor = Red,
        enabledTitle = false,
        enabledClick = true
    ) {
        override fun getValue(teamInformation: TeamInformation): String {
            return ""
        }
    }
;
    abstract fun getValue(teamInformation: TeamInformation): String
}