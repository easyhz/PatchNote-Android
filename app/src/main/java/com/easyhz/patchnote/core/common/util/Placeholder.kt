package com.easyhz.patchnote.core.common.util

import com.easyhz.patchnote.core.model.category.CategoryType
import java.util.Locale

fun getPostposition(selectedCategoryType: CategoryType): String {
    return if (Locale.getDefault().language == "ko") {
        when (selectedCategoryType) {
            CategoryType.UNIT -> "를"
            CategoryType.PART -> "를"
            else -> "을"
        }
    } else ""
}