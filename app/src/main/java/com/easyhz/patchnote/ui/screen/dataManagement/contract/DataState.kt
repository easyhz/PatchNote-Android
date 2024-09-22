package com.easyhz.patchnote.ui.screen.dataManagement.contract

import com.easyhz.patchnote.core.common.base.UiState
import com.easyhz.patchnote.core.model.category.Category

data class DataState(
    val category: List<Category>,
    val isShowDeleteDialog: Boolean,
    val deleteItem: String,
): UiState() {
    companion object {
        fun init() = DataState(
            category = emptyList(),
            isShowDeleteDialog = false,
            deleteItem = ""
        )
    }
}