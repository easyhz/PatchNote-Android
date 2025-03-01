package com.easyhz.patchnote.data.repository.defect

import androidx.paging.PagingData
import com.easyhz.patchnote.core.model.defect.DefectCompletion
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.defect.EntryDefect
import com.easyhz.patchnote.core.model.defect.OfflineDefect
import com.easyhz.patchnote.core.model.filter.FilterParam
import com.easyhz.patchnote.core.model.user.User
import kotlinx.coroutines.flow.Flow
import java.io.File

interface DefectRepository {
    suspend fun createDefect(param: EntryDefect): Result<Unit>
    suspend fun fetchDefects(filterParam: FilterParam, user: User): Result<List<DefectItem>>
    fun getDefectsPagingSource(filterParam: FilterParam, user: User): Flow<PagingData<DefectItem>>
    suspend fun fetchDefect(id: String): Result<DefectItem>
    suspend fun updateDefectCompletion(param: DefectCompletion): Result<Unit>
    suspend fun deleteDefect(id: String): Result<Unit>
    suspend fun exportDefects(defects: List<DefectItem>): Result<File>

    suspend fun saveOfflineDefect(defect: EntryDefect): Result<Unit>
    suspend fun getOfflineDefectsPagingSource(teamId: String, requesterId: String): Flow<PagingData<DefectItem>>

    fun findOfflineDefects(teamId: String, requesterId: String): List<OfflineDefect>
    suspend fun findOfflineDefect(defectId: String): Result<DefectItem>

    suspend fun deleteOfflineDefect(defectId: String): Result<Unit>
    suspend fun updateOfflineDefect(defect: EntryDefect): Result<Unit>
}