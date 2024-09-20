package com.easyhz.patchnote.core.model.category

import androidx.annotation.StringRes
import com.easyhz.patchnote.R

enum class CategoryType(
    @StringRes val nameId: Int,
){
    SITE(
        nameId = R.string.category_site
    ), SPACE(
        nameId = R.string.category_space
    ), PART(
        nameId = R.string.category_part
    ), WORK_TYPE(
        nameId = R.string.category_work_type
    )
}