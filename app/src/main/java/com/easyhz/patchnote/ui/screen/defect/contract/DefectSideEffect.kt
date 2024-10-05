package com.easyhz.patchnote.ui.screen.defect.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect
import com.easyhz.patchnote.core.model.category.CategoryType

sealed class DefectSideEffect: UiSideEffect() {
    data class ValidateEntryItem(val invalidEntry: CategoryType?): DefectSideEffect()
}