package com.easyhz.patchnote.ui.navigation.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.easyhz.patchnote.R
import com.easyhz.patchnote.ui.navigation.home.Home
import com.easyhz.patchnote.ui.navigation.offlineDefect.screen.OfflineDefect


enum class BottomMenuTabs(
    val qualifierName: String,
    @DrawableRes val iconId: Int,
    @StringRes val label: Int,
) {
    HOME(
        qualifierName = Home::class.java.name,
        iconId = R.drawable.ic_home,
        label = R.string.home
    ), OFFLINE_DEFECT(
        qualifierName = OfflineDefect::class.java.name,
        iconId = R.drawable.ic_offline_defect,
        label = R.string.offline_defect
    )
}