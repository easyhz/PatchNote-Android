package com.easyhz.patchnote.data.repository.category

import com.easyhz.patchnote.core.model.category.Category
import com.easyhz.patchnote.core.model.dataEntry.DataEntryItem
import com.easyhz.patchnote.data.datasource.remote.category.CategoryDataSource
import com.easyhz.patchnote.data.mapper.category.toModel
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDataSource: CategoryDataSource
): CategoryRepository {
    override suspend fun fetchCategory(teamId: String): Result<List<Category>> {
        return categoryDataSource.fetchCategory(teamId = teamId).map { it.toModel() }
    }

    override suspend fun updateCategory(teamId: String, dataList: List<DataEntryItem>): Result<Unit> {
        return categoryDataSource.updateCategory(teamId = teamId, dataList = dataList)
    }

    override suspend fun deleteCategory(teamId: String, category: String, index: Int): Result<Unit> {
        return categoryDataSource.deleteCategory(teamId = teamId, category = category, index = index)
    }
}