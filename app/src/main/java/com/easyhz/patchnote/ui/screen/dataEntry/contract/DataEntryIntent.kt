package com.easyhz.patchnote.ui.screen.dataEntry.contract

import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.core.model.category.CategoryType

sealed class DataEntryIntent: UiIntent() {
    data class SelectDataEntryItemCategoryType(val index: Int, val categoryType: CategoryType): DataEntryIntent()
    data class ChangeDataEntryItemValue(val index: Int, val value: String): DataEntryIntent()
    data class DeleteDataEntryItem(val index: Int): DataEntryIntent()
}