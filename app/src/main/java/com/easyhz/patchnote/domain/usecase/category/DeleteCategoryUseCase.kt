package com.easyhz.patchnote.domain.usecase.category

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.model.category.DeleteCategory
import com.easyhz.patchnote.data.repository.category.CategoryRepository
import com.easyhz.patchnote.data.repository.user.UserRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository
): BaseUseCase<DeleteCategory, Unit>() {
    override suspend fun invoke(param: DeleteCategory): Result<Unit> = runCatching {
        val user = userRepository.getUserFromLocal().getOrThrow()
        return categoryRepository.deleteCategory(teamId = user.teamId, category = param.category, index = param.index)
    }
}