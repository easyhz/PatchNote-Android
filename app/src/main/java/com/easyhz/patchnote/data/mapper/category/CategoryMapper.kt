package com.easyhz.patchnote.data.mapper.category

import com.easyhz.patchnote.core.model.category.Category
import com.easyhz.patchnote.data.model.category.response.CategoryResponse

fun CategoryResponse.toModel(): List<Category> = listOf(
    Category.Site(site.sorted()),
    Category.Space(space.sorted()),
    Category.Part(part.sorted()),
    Category.WorkType(workType.sorted())
)
