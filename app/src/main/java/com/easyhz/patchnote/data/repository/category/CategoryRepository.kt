package com.easyhz.patchnote.data.repository.category

import com.easyhz.patchnote.core.model.category.Category
import com.easyhz.patchnote.core.model.dataEntry.DataEntryItem

interface CategoryRepository {
    suspend fun fetchCategory(): Result<List<Category>>
    suspend fun updateCategory(dataList: List<DataEntryItem>): Result<Unit>
    suspend fun deleteCategory(category: String, index: Int): Result<Unit>
}