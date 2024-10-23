package com.easyhz.patchnote.data.datasource.remote.configuration

import com.easyhz.patchnote.core.common.constant.Collection.CONFIGURATION
import com.easyhz.patchnote.core.common.constant.Field
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.common.util.documentHandler
import com.easyhz.patchnote.data.model.configuration.ConfigurationData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ConfigurationDataSourceImpl @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val firestore: FirebaseFirestore
): ConfigurationDataSource {
    override suspend fun fetchConfiguration(): Result<ConfigurationData> = documentHandler(dispatcher) {
        firestore.collection(CONFIGURATION).document(Field.CONFIGURATION).get()
    }
}