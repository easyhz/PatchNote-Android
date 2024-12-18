package com.easyhz.patchnote.ui.screen.setting.my_page.contract

import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.core.model.error.DialogMessage
import com.easyhz.patchnote.core.model.setting.MyPageItem

sealed class MyPageIntent: UiIntent() {
    data object NavigateToUp: MyPageIntent()
    data class ClickMyPageItem(val myPageItem: MyPageItem): MyPageIntent()
    data class ShowError(val message: DialogMessage?): MyPageIntent()
}