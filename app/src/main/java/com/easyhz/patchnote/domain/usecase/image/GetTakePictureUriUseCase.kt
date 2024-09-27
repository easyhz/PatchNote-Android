package com.easyhz.patchnote.domain.usecase.image

import android.net.Uri
import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.data.repository.image.ImageRepository
import javax.inject.Inject

class GetTakePictureUriUseCase @Inject constructor(
    private val imageRepository: ImageRepository
): BaseUseCase<Unit, Uri>() {
    override suspend fun invoke(param: Unit): Result<Uri> =
        imageRepository.getTakePictureUri()
}