package com.easyhz.patchnote.data.datasource.defect

import com.easyhz.patchnote.core.common.constant.Collection.DEFECT
import com.easyhz.patchnote.core.common.constant.Field.AFTER_DESCRIPTION
import com.easyhz.patchnote.core.common.constant.Field.AFTER_IMAGE_SIZES
import com.easyhz.patchnote.core.common.constant.Field.AFTER_IMAGE_URLS
import com.easyhz.patchnote.core.common.constant.Field.COMPLETION_DATE
import com.easyhz.patchnote.core.common.constant.Field.COMPLETION_DATE_STR
import com.easyhz.patchnote.core.common.constant.Field.PROGRESS
import com.easyhz.patchnote.core.common.constant.Field.REQUEST_DATE
import com.easyhz.patchnote.core.common.constant.Field.SEARCH
import com.easyhz.patchnote.core.common.constant.Field.WORKER_ID
import com.easyhz.patchnote.core.common.constant.Field.WORKER_NAME
import com.easyhz.patchnote.core.common.constant.Field.WORKER_PHONE
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.common.util.documentHandler
import com.easyhz.patchnote.core.common.util.fetchHandler
import com.easyhz.patchnote.core.common.util.search
import com.easyhz.patchnote.core.common.util.setHandler
import com.easyhz.patchnote.core.model.defect.DefectProgress
import com.easyhz.patchnote.data.model.defect.data.DefectCompletionData
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

    override suspend fun updateDefectCompletion(id: String, data: DefectCompletionData): Result<Unit> = setHandler(dispatcher) {
        println("updateDefectCompletion id: $id, data: $data")
        firestore.runTransaction { transaction ->
            val docRef = firestore.collection(DEFECT).document(id)
            transaction.update(docRef, PROGRESS, DefectProgress.DONE)
            transaction.update(docRef, AFTER_DESCRIPTION, data.afterDescription)
            transaction.update(docRef, AFTER_IMAGE_URLS, data.afterImageUrls)
            transaction.update(docRef, AFTER_IMAGE_SIZES, data.afterImageSizes)
            transaction.update(docRef, WORKER_ID, data.workerId)
            transaction.update(docRef, WORKER_NAME, data.workerName)
            transaction.update(docRef, WORKER_PHONE, data.workerPhone)
            transaction.update(docRef, COMPLETION_DATE, data.completionDate)
            transaction.update(docRef, COMPLETION_DATE_STR, data.completionDateStr)

            null
        }
    }
}