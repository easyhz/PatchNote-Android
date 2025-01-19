package com.easyhz.patchnote.domain.usecase.reception

import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.data.repository.reception.ReceptionRepository
import javax.inject.Inject

class SetReceptionSettingUseCase @Inject constructor(
    private val receptionRepository: ReceptionRepository
) {
    suspend fun invoke(categoryType: CategoryType, newValue: Boolean): Result<Unit> {
        return runCatching {
            receptionRepository.setReceptionSetting(categoryType, newValue)
        }
    }
}