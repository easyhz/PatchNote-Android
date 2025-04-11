package com.easyhz.patchnote.domain.usecase.image

import com.easyhz.patchnote.core.model.image.DisplayImageType
import com.easyhz.patchnote.data.repository.image.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImageSettingUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    operator fun invoke(): Flow<LinkedHashMap<DisplayImageType, Boolean>> {
        return imageRepository.getImageSetting()
    }
}