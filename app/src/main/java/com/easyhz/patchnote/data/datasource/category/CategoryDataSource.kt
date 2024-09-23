package com.easyhz.patchnote.data.datasource.category

import com.easyhz.patchnote.core.model.dataEntry.DataEntryItem
import com.easyhz.patchnote.data.model.category.response.CategoryResponse

interface CategoryDataSource {
    suspend fun fetchCategory(): Result<CategoryResponse>
    suspend fun updateCategory(dataList: List<DataEntryItem>): Result<Unit>
    suspend fun deleteCategory(category: String, index: Int): Result<Unit>
}