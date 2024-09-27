package com.easyhz.patchnote.data.repository.image

import android.content.Context
import android.net.Uri
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.data.provider.PatchNoteFileProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
) : ImageRepository {
    override suspend fun getTakePictureUri(): Result<Uri> =
        PatchNoteFileProvider.getTakePictureUri(context, dispatcher)
}
