package com.easyhz.patchnote.ui.screen.home.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.defect.DefectItem

data class HomeState(
    val defectList: List<DefectItem>,
    val isRefreshing: Boolean,
): UiState() {
    companion object {
        fun init() = HomeState(
            defectList = emptyList(),
            isRefreshing = false
        )
    }
}
