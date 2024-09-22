package com.easyhz.patchnote.data.datasource.category

import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.common.util.documentHandler
import com.easyhz.patchnote.core.common.util.setHandler
import com.easyhz.patchnote.data.model.category.response.CategoryResponse
import com.easyhz.patchnote.data.util.Collections.CATEGORY
import com.easyhz.patchnote.data.util.Fields.CATEGORY_DATA
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CategoryDataSourceImpl @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val firestore: FirebaseFirestore
): CategoryDataSource {
    override suspend fun fetchCategory(): Result<CategoryResponse> = documentHandler(dispatcher) {
        firestore.collection(CATEGORY).document(CATEGORY_DATA).get()
    }

    override suspend fun deleteCategory(category: String, index: Int): Result<Unit> = setHandler(dispatcher) {
        firestore.runTransaction { transaction ->
            val docRef = firestore.collection(CATEGORY).document(CATEGORY_DATA)
            val snapshot = transaction.get(docRef)
            val categoryList = snapshot.get(category) as ArrayList<String>
            categoryList.removeAt(index)
            transaction.update(docRef, category, categoryList)
            null
        }
    }
}