package com.easyhz.patchnote.data.mapper.category

import com.easyhz.patchnote.core.model.category.Category
import com.easyhz.patchnote.data.model.category.response.CategoryResponse

fun CategoryResponse.toModel(): List<Category> = listOf(
    Category.Site(site),
    Category.Space(space),
    Category.Part(part),
    Category.WorkType(workType)
)
