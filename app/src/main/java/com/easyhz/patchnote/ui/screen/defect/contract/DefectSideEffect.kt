package com.easyhz.patchnote.ui.screen.defect.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.error.DialogMessage

sealed class DefectSideEffect: UiSideEffect() {
    data class ValidateEntryItem(val invalidEntry: CategoryType?): DefectSideEffect()
    data class SendError(val message: DialogMessage): DefectSideEffect()
    data class SendLoading(val isLoading: Boolean): DefectSideEffect()
    data class SearchItem(val item: LinkedHashMap<String, String>): DefectSideEffect()
}