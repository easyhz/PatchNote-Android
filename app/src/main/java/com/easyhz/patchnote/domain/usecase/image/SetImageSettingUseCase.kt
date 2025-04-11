package com.easyhz.patchnote.domain.usecase.image

import com.easyhz.patchnote.core.model.image.DisplayImageType
import com.easyhz.patchnote.data.repository.image.ImageRepository
import javax.inject.Inject

class SetImageSettingUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(displayImageType: DisplayImageType, newValue: Boolean): Result<Unit> = runCatching {
        imageRepository.setImageSetting(displayImageType, newValue)
    }
}