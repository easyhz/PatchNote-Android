package com.easyhz.patchnote.core.common.helper.image

import android.graphics.Bitmap

interface ImageDownloadHelper {
    fun saveImage(bitmap: Bitmap)
    fun loadBitmapFromUrl(url: String): Bitmap?
}