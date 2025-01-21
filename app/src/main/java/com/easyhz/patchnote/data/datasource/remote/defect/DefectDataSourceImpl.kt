package com.easyhz.patchnote.data.datasource.remote.defect

import com.easyhz.patchnote.core.common.constant.Collection.DEFECT
import com.easyhz.patchnote.core.common.constant.Field.AFTER_DESCRIPTION
import com.easyhz.patchnote.core.common.constant.Field.AFTER_IMAGE_SIZES
import com.easyhz.patchnote.core.common.constant.Field.AFTER_IMAGE_URLS
import com.easyhz.patchnote.core.common.constant.Field.COMPLETION_DATE
import com.easyhz.patchnote.core.common.constant.Field.COMPLETION_DATE_STR
import com.easyhz.patchnote.core.common.constant.Field.DELETION_DATE
import com.easyhz.patchnote.core.common.constant.Field.IS_DELETED
import com.easyhz.patchnote.core.common.constant.Field.PROGRESS
import com.easyhz.patchnote.core.common.constant.Field.REQUEST_DATE
import com.easyhz.patchnote.core.common.constant.Field.SEARCH
import com.easyhz.patchnote.core.common.constant.Field.TEAM_ID
import com.easyhz.patchnote.core.common.constant.Field.WORKER_ID
import com.easyhz.patchnote.core.common.constant.Field.WORKER_NAME
import com.easyhz.patchnote.core.common.constant.Field.WORKER_PHONE
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.common.util.documentHandler
import com.easyhz.patchnote.core.common.util.fetchHandler
import com.easyhz.patchnote.core.common.util.indexSearch
import com.easyhz.patchnote.core.common.util.indexSearchDate
import com.easyhz.patchnote.core.common.util.paging
import com.easyhz.patchnote.core.common.util.search
import com.easyhz.patchnote.core.common.util.setHandler
import com.easyhz.patchnote.core.model.defect.DefectProgress
import com.easyhz.patchnote.core.model.filter.IndexField
import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.core.model.util.Paging
import com.easyhz.patchnote.data.model.defect.data.DefectCompletionData
import com.easyhz.patchnote.data.model.defect.data.DefectData
import com.google.firebase.Timestamp
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

    override suspend fun fetchDefects(
        search: String?,
        index: IndexField,
        user: User,
        paging: Paging<Timestamp>?
    ): Result<List<DefectData>> =
        fetchHandler(dispatcher) {
            firestore.collection(DEFECT)
                .search(SEARCH, search)
                .indexSearch(PROGRESS, index.progress)
                .indexSearchDate(REQUEST_DATE, index.requestDate)
                .indexSearch(WORKER_NAME, index.workerName)
                .indexSearch(COMPLETION_DATE_STR, index.completionDate)
                .whereEqualTo(TEAM_ID, user.teamId)
                .whereEqualTo(IS_DELETED, false)
                .orderBy(REQUEST_DATE, Direction.DESCENDING)
                .paging(paging)
                .get()
        }

    override suspend fun fetchDefect(id: String): Result<DefectData> = documentHandler(dispatcher) {
        firestore.collection(DEFECT).document(id).get()
    }

    override suspend fun updateDefectCompletion(id: String, data: DefectCompletionData): Result<Unit> = setHandler(dispatcher) {
        firestore.runTransaction { transaction ->
            val docRef = firestore.collection(DEFECT).document(id)

            val updateData = mapOf(
                PROGRESS to DefectProgress.DONE.name,
                AFTER_DESCRIPTION to data.afterDescription,
                AFTER_IMAGE_URLS to data.afterImageUrls,
                AFTER_IMAGE_SIZES to data.afterImageSizes,
                WORKER_ID to data.workerId,
                WORKER_NAME to data.workerName,
                WORKER_PHONE to data.workerPhone,
                COMPLETION_DATE to data.completionDate,
                COMPLETION_DATE_STR to data.completionDateStr
            )

            transaction.update(docRef, updateData)
            null
        }
    }

    override suspend fun deleteDefect(id: String): Result<Unit> = setHandler(dispatcher) {
        firestore.runTransaction { transaction ->
            val docRef = firestore.collection(DEFECT).document(id)
            transaction.update(docRef, IS_DELETED, true)
            transaction.update(docRef, DELETION_DATE, Timestamp.now())
            null
        }
    }
}