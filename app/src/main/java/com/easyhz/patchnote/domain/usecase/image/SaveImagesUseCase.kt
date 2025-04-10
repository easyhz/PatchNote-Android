package com.easyhz.patchnote.domain.usecase.image

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.data.repository.image.ImageRepository
import javax.inject.Inject

class SaveImagesUseCase @Inject constructor(
    private val imageRepository: ImageRepository
): BaseUseCase<List<String>, Unit>() {
    override suspend fun invoke(param: List<String>): Result<Unit> {
        return imageRepository.downloadImage(param)
    }
}