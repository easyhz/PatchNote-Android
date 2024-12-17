package com.easyhz.patchnote.domain.usecase.category

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.model.category.Category
import com.easyhz.patchnote.data.repository.category.CategoryRepository
import com.easyhz.patchnote.data.repository.user.UserRepository
import javax.inject.Inject

class FetchCategoryUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository
): BaseUseCase<Unit, List<Category>>() {
    override suspend fun invoke(param: Unit): Result<List<Category>> = runCatching {
        val user = userRepository.getUserFromLocal().getOrThrow()
        return categoryRepository.fetchCategory(teamId = user.teamId)
    }
}