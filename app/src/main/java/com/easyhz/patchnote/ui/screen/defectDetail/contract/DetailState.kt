package com.easyhz.patchnote.ui.screen.defectDetail.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.defect.DefectItem

data class DetailState(
    val isLoading: Boolean,
    val defectItem: DefectItem?,
): UiState() {
    companion object {
        fun init() = DetailState(
            isLoading = false,
            defectItem = null
        )
    }
}
