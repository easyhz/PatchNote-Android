package com.easyhz.patchnote.data.datasource.remote.defect

import com.easyhz.patchnote.core.model.filter.IndexField
import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.core.model.util.Paging
import com.easyhz.patchnote.data.model.defect.data.DefectCompletionData
import com.easyhz.patchnote.data.model.defect.data.DefectData
import com.google.firebase.Timestamp

interface DefectDataSource {
    suspend fun createDefect(data: DefectData): Result<Unit>
    suspend fun fetchDefects(search: String?, index: IndexField, user: User, paging: Paging<Timestamp>?): Result<List<DefectData>>
    suspend fun fetchDefect(id: String): Result<DefectData>
    suspend fun updateDefectCompletion(id: String, data: DefectCompletionData): Result<Unit>
    suspend fun deleteDefect(id: String): Result<Unit>
}