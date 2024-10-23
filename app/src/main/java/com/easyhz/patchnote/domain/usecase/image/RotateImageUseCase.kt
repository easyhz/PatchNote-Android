package com.easyhz.patchnote.domain.usecase.image

import android.net.Uri
import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.data.repository.image.ImageRepository
import javax.inject.Inject

class RotateImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository
): BaseUseCase<Uri, Unit>() {
    override suspend fun invoke(param: Uri): Result<Unit> {
        return imageRepository.rotateImage(param)
    }
}