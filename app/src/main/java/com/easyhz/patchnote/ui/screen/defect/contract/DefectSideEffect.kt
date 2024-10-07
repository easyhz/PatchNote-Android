package com.easyhz.patchnote.ui.screen.defect.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.error.ErrorMessage

sealed class DefectSideEffect: UiSideEffect() {
    data class ValidateEntryItem(val invalidEntry: CategoryType?): DefectSideEffect()
    data class SendError(val message: ErrorMessage): DefectSideEffect()
    data class SendLoading(val isLoading: Boolean): DefectSideEffect()
}