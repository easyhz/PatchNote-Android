package com.easyhz.patchnote.data.repository.reception

import com.easyhz.patchnote.core.model.category.CategoryType
import kotlinx.coroutines.flow.Flow

interface ReceptionRepository {
    fun getReceptionSetting(): Flow<LinkedHashMap<CategoryType, Boolean>>
    suspend fun setReceptionSetting(categoryType: CategoryType, newValue: Boolean): Unit
}