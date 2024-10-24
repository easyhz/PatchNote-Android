package com.easyhz.patchnote.data.datasource.remote.category

import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.common.util.documentHandler
import com.easyhz.patchnote.core.common.util.setHandler
import com.easyhz.patchnote.core.model.dataEntry.DataEntryItem
import com.easyhz.patchnote.core.common.constant.Collection.CATEGORY
import com.easyhz.patchnote.core.common.constant.Field.CATEGORY_DATA
import com.easyhz.patchnote.data.model.category.response.CategoryResponse
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

    override suspend fun updateCategory(dataList: List<DataEntryItem>): Result<Unit> = setHandler(dispatcher) {
        firestore.runTransaction { transaction ->
            val docRef = firestore.collection(CATEGORY).document(CATEGORY_DATA)
            val snapshot = transaction.get(docRef)
            val groupedData = dataList.groupBy { it.categoryType.alias }
            groupedData.forEach { (category, items) ->
                val categoryList = snapshot.get(category) as ArrayList<String>
                val newValues = items.map { it.value }
                categoryList.addAll(newValues)
                transaction.update(docRef, category, categoryList)
            }
            null
        }
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