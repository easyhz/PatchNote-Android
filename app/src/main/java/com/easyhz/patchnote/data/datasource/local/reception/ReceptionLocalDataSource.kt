package com.easyhz.patchnote.data.datasource.local.reception

import com.easyhz.patchnote.core.model.category.CategoryType
import kotlinx.coroutines.flow.Flow

interface ReceptionLocalDataSource {
    fun getReceptionSetting(): Flow<LinkedHashMap<CategoryType, Boolean>>
    suspend fun setReceptionSetting(categoryType: CategoryType, newValue: Boolean): Unit
}