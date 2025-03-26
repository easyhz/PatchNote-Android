package com.easyhz.patchnote.domain.usecase.category

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.common.error.AppError
import com.easyhz.patchnote.core.model.dataEntry.DataEntryItem
import com.easyhz.patchnote.data.repository.category.CategoryRepository
import com.easyhz.patchnote.data.repository.user.UserRepository
import javax.inject.Inject

class UpdateCategoryUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository
): BaseUseCase<List<DataEntryItem>, Unit>() {
    override suspend fun invoke(param: List<DataEntryItem>): Result<Unit> {
        val user = userRepository.getUserFromLocal().getOrThrow()
        if (user.currentTeamId == null) {
            throw AppError.NoTeamDataError
        }
        return categoryRepository.updateCategory(teamId = user.currentTeamId, dataList = param)
    }
}