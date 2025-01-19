package com.easyhz.patchnote.domain.usecase.reception

import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.user.UserInformation
import com.easyhz.patchnote.data.repository.reception.ReceptionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReceptionSettingUseCase @Inject constructor(
    private val receptionRepository: ReceptionRepository
) {
    fun invoke(): Flow<LinkedHashMap<CategoryType, Boolean>> {
        return receptionRepository.getReceptionSetting()
    }
}