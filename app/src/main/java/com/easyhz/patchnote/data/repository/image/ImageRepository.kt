package com.easyhz.patchnote.data.repository.image

import android.net.Uri
import com.easyhz.patchnote.core.model.image.DefectImage
import com.easyhz.patchnote.core.model.image.ImageSize

interface ImageRepository {
    suspend fun getTakePictureUri(): Result<Uri>
    suspend fun uploadImages(pathId: String, images: List<Uri>): Result<List<String>>
    suspend fun uploadThumbnail(pathId: String, imageUri: Uri): Result<String>
    suspend fun getImageSizes(imageUri: List<Uri>): Result<List<ImageSize>>
    suspend fun rotateImage(imageUri: Uri): Result<Unit>
    suspend fun saveOfflineImages(imageUri: List<Uri>): Result<List<Uri?>>
    suspend fun getDefectImages(imageUrls: List<String>): Result<List<DefectImage>>

    suspend fun downloadImage(images: List<String>): Result<Unit>
}