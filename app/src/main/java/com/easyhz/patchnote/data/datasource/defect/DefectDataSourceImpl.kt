package com.easyhz.patchnote.data.datasource.defect

import com.easyhz.patchnote.core.common.constant.Collection.DEFECT
import com.easyhz.patchnote.core.common.constant.Field.PROGRESS
import com.easyhz.patchnote.core.common.constant.Field.REQUEST_DATE
import com.easyhz.patchnote.core.common.constant.Field.SEARCH
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.common.util.documentHandler
import com.easyhz.patchnote.core.common.util.fetchHandler
import com.easyhz.patchnote.core.common.util.search
import com.easyhz.patchnote.core.common.util.setHandler
import com.easyhz.patchnote.core.model.defect.DefectProgress
import com.easyhz.patchnote.data.model.defect.data.CompletionData
import com.easyhz.patchnote.data.model.defect.data.DefectData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query.Direction
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DefectDataSourceImpl @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val firestore: FirebaseFirestore
) : DefectDataSource {
    override suspend fun createDefect(data: DefectData): Result<Unit> = setHandler(dispatcher) {
        firestore.collection(DEFECT).document(data.id).set(data)
    }

    override suspend fun fetchDefects(search: String?): Result<List<DefectData>> =
        fetchHandler(dispatcher) {
            firestore.collection(DEFECT)
                .search(SEARCH, search)
                .orderBy(REQUEST_DATE, Direction.DESCENDING)
                .get()
        }

    override suspend fun fetchDefect(id: String): Result<DefectData> = documentHandler(dispatcher) {
        firestore.collection(DEFECT).document(id).get()
    }

    override suspend fun updateDefectCompletion(id: String, data: CompletionData): Result<Unit> = setHandler(dispatcher) {
        firestore.runTransaction { transaction ->
            val docRef = firestore.collection(DEFECT).document(id)
            transaction.update(docRef, PROGRESS, DefectProgress.DONE)
            transaction.set(docRef, data)

            null
        }
    }
}