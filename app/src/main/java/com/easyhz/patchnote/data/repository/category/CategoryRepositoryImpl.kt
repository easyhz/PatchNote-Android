package com.easyhz.patchnote.data.repository.category

import com.easyhz.patchnote.core.model.category.Category
import com.easyhz.patchnote.core.model.dataEntry.DataEntryItem
import com.easyhz.patchnote.data.datasource.remote.category.CategoryDataSource
import com.easyhz.patchnote.data.mapper.category.toModel
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDataSource: CategoryDataSource
): CategoryRepository {
    override suspend fun fetchCategory(): Result<List<Category>> {
        return categoryDataSource.fetchCategory().map { it.toModel() }
    }

    override suspend fun updateCategory(dataList: List<DataEntryItem>): Result<Unit> {
        return categoryDataSource.updateCategory(dataList)
    }

    override suspend fun deleteCategory(category: String, index: Int): Result<Unit> {
        return categoryDataSource.deleteCategory(category, index)
    }
}