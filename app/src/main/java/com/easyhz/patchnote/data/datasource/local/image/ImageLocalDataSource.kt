package com.easyhz.patchnote.data.datasource.local.image

import com.easyhz.patchnote.core.model.image.DisplayImageType
import kotlinx.coroutines.flow.Flow

interface ImageLocalDataSource {
    fun getImageSetting(): Flow<LinkedHashMap<DisplayImageType, Boolean>>
    suspend fun setImageSetting(categoryType: DisplayImageType, newValue: Boolean): Unit
}