package com.easyhz.patchnote.data.datasource.remote.category

import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.common.util.documentHandler
import com.easyhz.patchnote.core.common.util.setHandler
import com.easyhz.patchnote.core.model.dataEntry.DataEntryItem
import com.easyhz.patchnote.core.common.constant.Collection.CATEGORY
import com.easyhz.patchnote.data.model.category.response.CategoryResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CategoryDataSourceImpl @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val firestore: FirebaseFirestore
): CategoryDataSource {
    override suspend fun fetchCategory(teamId: String): Result<CategoryResponse> = documentHandler(dispatcher) {
        firestore.collection(CATEGORY).document(teamId).get()
    }

    override suspend fun updateCategory(teamId: String, dataList: List<DataEntryItem>): Result<Unit> = setHandler(dispatcher) {
        firestore.runTransaction { transaction ->
            val docRef = firestore.collection(CATEGORY).document(teamId)
            val snapshot = transaction.get(docRef)
            if (!snapshot.exists()) {
                val initialData = dataList.groupBy { it.categoryType.alias }
                    .mapValues { ArrayList<String>() }
                transaction.set(docRef, initialData)
            }
            val groupedData = dataList.groupBy { it.categoryType.alias }
            groupedData.forEach { (category, items) ->
                val categoryList = snapshot.get(category) as ArrayList<String>? ?: ArrayList()
                val newValues = items.map { it.value }
                categoryList.addAll(newValues)
                transaction.update(docRef, category, categoryList)
            }
            null
        }
    }

    override suspend fun deleteCategory(teamId: String, category: String, index: Int): Result<Unit> = setHandler(dispatcher) {
        firestore.runTransaction { transaction ->
            val docRef = firestore.collection(CATEGORY).document(teamId)
            val snapshot = transaction.get(docRef)
            val categoryList = snapshot.get(category) as ArrayList<String>? ?: ArrayList()
            categoryList.removeAt(index)
            transaction.update(docRef, category, categoryList)
            null
        }
    }
}