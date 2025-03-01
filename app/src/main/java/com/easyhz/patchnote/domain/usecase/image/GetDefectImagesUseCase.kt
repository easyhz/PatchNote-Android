package com.easyhz.patchnote.domain.usecase.image

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.model.image.DefectImage
import com.easyhz.patchnote.data.repository.image.ImageRepository
import javax.inject.Inject

class GetDefectImagesUseCase @Inject constructor(
    private val imageRepository: ImageRepository
): BaseUseCase<List<String>, List<DefectImage>>() {
    override suspend fun invoke(param: List<String>): Result<List<DefectImage>> {
        return imageRepository.getDefectImages(param)
    }
}