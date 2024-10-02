package com.easyhz.patchnote.data.datasource.defect

import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.common.util.setHandler
import com.easyhz.patchnote.core.common.constant.Collection.DEFECT
import com.easyhz.patchnote.data.model.defect.data.DefectData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DefectDataSourceImpl @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val firestore: FirebaseFirestore
): DefectDataSource {
    override suspend fun createDefect(data: DefectData): Result<Unit> = setHandler(dispatcher) {
        firestore.collection(DEFECT).document(data.id).set(data)
    }
}