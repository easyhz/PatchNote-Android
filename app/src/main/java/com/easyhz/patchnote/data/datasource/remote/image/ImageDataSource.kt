package com.easyhz.patchnote.data.datasource.remote.image

import android.content.Context
import android.net.Uri

interface ImageDataSource {
    suspend fun uploadImage(pathId: String, imageUri: Uri, imageName: String): Result<String>
    suspend fun downloadImage(context: Context, imageUrl: String, id: String): Result<Uri>
}