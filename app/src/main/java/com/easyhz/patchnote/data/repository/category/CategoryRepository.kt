package com.easyhz.patchnote.data.repository.category

import com.easyhz.patchnote.core.model.category.Category
import com.easyhz.patchnote.core.model.dataEntry.DataEntryItem

interface CategoryRepository {
    suspend fun fetchCategory(teamId: String): Result<List<Category>>
    suspend fun updateCategory(teamId: String, dataList: List<DataEntryItem>): Result<Unit>
    suspend fun deleteCategory(teamId: String, category: String, index: Int): Result<Unit>
}