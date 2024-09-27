package com.easyhz.patchnote.data.repository.image

import android.net.Uri

interface ImageRepository {
    suspend fun getTakePictureUri(): Result<Uri>
}