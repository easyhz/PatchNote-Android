package com.easyhz.patchnote.data.repository.category

import com.easyhz.patchnote.core.model.category.Category
import com.easyhz.patchnote.data.datasource.category.CategoryDataSource
import com.easyhz.patchnote.data.mapper.category.toModel
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDataSource: CategoryDataSource
): CategoryRepository {
    override suspend fun fetchCategory(): Result<List<Category>> {
        return categoryDataSource.fetchCategory().map { it.toModel() }
    }
}