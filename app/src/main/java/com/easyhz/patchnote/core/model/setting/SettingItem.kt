package com.easyhz.patchnote.core.model.setting

import androidx.annotation.StringRes
import com.easyhz.patchnote.BuildConfig
import com.easyhz.patchnote.R

interface SettingItem {
    val stringResId: Int
    val enabledClick: Boolean
        get() = true
    fun getValue(): String? = null
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
    TEAM_INFORMATION {
       override val stringResId: Int
           get() = R.string.setting_team_information
    }, DATA_MANAGEMENT {
        override val stringResId: Int
            get() = R.string.setting_data_management
    }, TEAM_LIST {
        override val stringResId: Int
            get() = R.string.setting_team_list
    }, ;
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

        override fun getValue(): String? {
            return null
        }
    }, SUPPORT {
        override val stringResId: Int
            get() = R.string.setting_support

        override fun getValue(): String {
            return "https://open.kakao.com/o/gI5mQ2Vg"
        }

    }, BLOCK {
        override val stringResId: Int
            get() = R.string.setting_block

        override fun getValue(): String? {
            return null
        }
    }, VERSION {
        override val stringResId: Int
            get() = R.string.setting_version

        override val enabledClick: Boolean
            get() = false

        override fun getValue(): String {
            return "v${BuildConfig.VERSION_NAME}"
        }
    };
}