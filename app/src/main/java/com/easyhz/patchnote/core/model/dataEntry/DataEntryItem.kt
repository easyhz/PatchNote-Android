package com.easyhz.patchnote.core.model.dataEntry

import com.easyhz.patchnote.core.common.util.Generate
import com.easyhz.patchnote.core.model.category.CategoryType

data class DataEntryItem(
    val id: String,
    val categoryType: CategoryType,
    val value: String
) {
    companion object {
        fun init() = DataEntryItem(
            id = Generate.randomUUID(),
            categoryType = CategoryType.SITE,
            value = ""
        )
    }
}
