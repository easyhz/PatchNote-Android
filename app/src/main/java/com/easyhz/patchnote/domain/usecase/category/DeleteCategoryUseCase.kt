package com.easyhz.patchnote.domain.usecase.category

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.model.category.DeleteCategory
import com.easyhz.patchnote.data.repository.category.CategoryRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
): BaseUseCase<DeleteCategory, Unit>() {
    override suspend fun invoke(param: DeleteCategory): Result<Unit> {
        return categoryRepository.deleteCategory(param.category, param.index)
    }
}