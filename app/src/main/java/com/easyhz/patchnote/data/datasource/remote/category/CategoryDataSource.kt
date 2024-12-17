package com.easyhz.patchnote.data.datasource.remote.category

import com.easyhz.patchnote.core.model.dataEntry.DataEntryItem
import com.easyhz.patchnote.data.model.category.response.CategoryResponse

interface CategoryDataSource {
    suspend fun fetchCategory(teamId: String): Result<CategoryResponse>
    suspend fun updateCategory(teamId: String, dataList: List<DataEntryItem>): Result<Unit>
    suspend fun deleteCategory(teamId: String, category: String, index: Int): Result<Unit>
}