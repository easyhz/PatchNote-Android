package com.easyhz.patchnote.core.model.category

import androidx.annotation.StringRes
import com.easyhz.patchnote.R

enum class CategoryType(
    @StringRes val nameId: Int,
    val alias: String,
    val enabledEntry: Boolean = true
){
    SITE(
        nameId = R.string.category_site,
        alias = "site"
    ), BUILDING(
        nameId = R.string.category_building,
        alias = "building",
        enabledEntry = false,
    ), UNIT(
        nameId = R.string.category_unit,
        alias = "unit",
        enabledEntry = false
    ), SPACE(
        nameId = R.string.category_space,
        alias = "space"
    ), PART(
        nameId = R.string.category_part,
        alias = "part"
    ), WORK_TYPE(
        nameId = R.string.category_work_type,
        alias = "workType"
    );
    companion object{
        fun toLinkedHashMap(): LinkedHashMap<CategoryType, String> {
            return LinkedHashMap(entries.associateWith { "" })
        }

        fun toMap(): Map<CategoryType, List<String>> {
            return entries.associateWith { emptyList() }
        }
    }
}