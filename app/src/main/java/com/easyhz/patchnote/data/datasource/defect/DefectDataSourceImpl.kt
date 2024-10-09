package com.easyhz.patchnote.data.datasource.defect

import com.easyhz.patchnote.core.common.constant.Collection.DEFECT
import com.easyhz.patchnote.core.common.constant.Field.REQUEST_DATE
import com.easyhz.patchnote.core.common.constant.Field.SEARCH
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.common.util.fetchHandler
import com.easyhz.patchnote.core.common.util.search
import com.easyhz.patchnote.core.common.util.setHandler
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

    override suspend fun fetchDefects(search: List<String>?): Result<List<DefectData>> =
        fetchHandler {
            firestore.collection(DEFECT)
                .search(SEARCH, search)
                .orderBy(REQUEST_DATE, Direction.DESCENDING)
                .get()
        }
}