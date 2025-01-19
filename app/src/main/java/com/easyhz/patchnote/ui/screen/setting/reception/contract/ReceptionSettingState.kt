package com.easyhz.patchnote.ui.screen.setting.reception.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.category.CategoryType

data class ReceptionSettingState(
    val items: LinkedHashMap<CategoryType, Boolean>
): UiState() {
    companion object {
        fun init() = ReceptionSettingState(
            items = linkedMapOf(
                CategoryType.SITE to true,
                CategoryType.BUILDING to true,
                CategoryType.UNIT to true,
                CategoryType.SPACE to true,
                CategoryType.PART to false,
                CategoryType.WORK_TYPE to false,
            )
        )
    }
}
