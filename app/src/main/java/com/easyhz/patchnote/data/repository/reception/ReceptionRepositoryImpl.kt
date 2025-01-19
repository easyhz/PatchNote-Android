package com.easyhz.patchnote.data.repository.reception

import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.data.datasource.local.reception.ReceptionLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReceptionRepositoryImpl @Inject constructor(
    private val receptionLocalDataSource: ReceptionLocalDataSource
): ReceptionRepository {
    override fun getReceptionSetting(): Flow<LinkedHashMap<CategoryType, Boolean>> {
        return receptionLocalDataSource.getReceptionSetting()
    }

    override suspend fun setReceptionSetting(categoryType: CategoryType, newValue: Boolean) {
        receptionLocalDataSource.setReceptionSetting(categoryType, newValue)
    }
}