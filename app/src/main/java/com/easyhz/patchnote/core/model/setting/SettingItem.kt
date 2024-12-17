package com.easyhz.patchnote.core.model.setting

import androidx.annotation.StringRes
import com.easyhz.patchnote.R

enum class SettingItem(
    @StringRes val stringResId: Int
) {
    ABOUT(
        stringResId = R.string.setting_about
    ), DATA_MANAGEMENT(
        stringResId = R.string.setting_data_management
    ), MY_PAGE(
        stringResId = R.string.setting_my_page
    )
}