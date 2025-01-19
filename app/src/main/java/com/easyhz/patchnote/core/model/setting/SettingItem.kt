package com.easyhz.patchnote.core.model.setting

import androidx.annotation.StringRes
import com.easyhz.patchnote.R

interface SettingItem {
    val stringResId: Int
}

enum class MajorSettingItem(
    @StringRes val stringResId: Int,
    val items: List<SettingItem>
) {
    TEAM(
        stringResId = R.string.setting_major_team,
        items = TeamSettingItem.entries
    ), MY(
        stringResId = R.string.setting_major_my,
        items = MySettingItem.entries
    ), ETC(
        stringResId = R.string.setting_major_etc,
        items = EtcSettingItem.entries
    )
}

enum class TeamSettingItem: SettingItem {
    DATA_MANAGEMENT {
        override val stringResId: Int
            get() = R.string.setting_data_management
    };
}

enum class MySettingItem: SettingItem {
    MY_PAGE {
        override val stringResId: Int
            get() = R.string.setting_my_page
    }, RECEPTION_SETTINGS {
        override val stringResId: Int
            get() = R.string.setting_reception_settings
    };
}

enum class EtcSettingItem: SettingItem {
    ABOUT {
        override val stringResId: Int
            get() = R.string.setting_about
    }, BLOCK {
        override val stringResId: Int
            get() = R.string.setting_block
    };
}