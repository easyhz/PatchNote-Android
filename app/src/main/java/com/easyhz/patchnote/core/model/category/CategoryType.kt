package com.easyhz.patchnote.core.model.category

import androidx.annotation.StringRes
import androidx.compose.ui.text.input.TextFieldValue
import com.easyhz.patchnote.R

enum class CategoryType(
    @StringRes val nameId: Int,
    val alias: String,
    val enabledEntry: Boolean = true,
    val enableClear: Boolean = true
){
    SITE(
        nameId = R.string.category_site,
        alias = "site",
        enableClear = false
    ), BUILDING(
        nameId = R.string.category_building,
        alias = "building",
        enabledEntry = false,
        enableClear = false
    ), UNIT(
        nameId = R.string.category_unit,
        alias = "unit",
        enabledEntry = false,
        enableClear = false
    ), SPACE(
        nameId = R.string.category_space,
        alias = "space",
    ), PART(
        nameId = R.string.category_part,
        alias = "part"
    ), WORK_TYPE(
        nameId = R.string.category_work_type,
        alias = "workType"
    );
    companion object{
        fun toLinkedHashMapTextFieldValue(): LinkedHashMap<CategoryType, TextFieldValue> {
            return LinkedHashMap(entries.associateWith { TextFieldValue("") })
        }

        fun toMapListString(): Map<CategoryType, List<String>> {
            return entries.associateWith { emptyList() }
        }
    }
}