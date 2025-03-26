package com.easyhz.patchnote.core.model.setting

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.toDateString
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
            return formatPhoneNumber(userInformation.user.phone)
        }
    }, CREATION_DATE(
        titleResId = R.string.setting_my_page_creation_date,
        enabledTitle = true,
        enabledClick = false
    ) {
        override fun getValue(userInformation: UserInformation): String {
            return userInformation.user.creationTime.toDateString()
        }
    }, LOGOUT(
        titleResId = R.string.setting_my_page_logout,
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

    fun formatPhoneNumber(phone: String): String {
        var number = phone.removePrefix("+82")

        if (!number.startsWith("0")) {
            number = "0$number"
        }

        return if (number.length == 11) {
            "${number.substring(0, 3)}-${number.substring(3, 7)}-${number.substring(7)}"
        } else {
            number
        }
    }
}