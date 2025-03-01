package com.easyhz.patchnote.data.datasource.local.defect

import androidx.paging.PagingSource
import com.easyhz.patchnote.core.database.defect.model.OfflineDefect

interface DefectLocalDataSource {
    fun findOfflineDefectsPagingSource(teamId: String, requesterId: String): PagingSource<Int, OfflineDefect>

    fun findOfflineDefects(teamId: String, requesterId: String): List<OfflineDefect>

    fun findOfflineDefect(defectId: String): Result<OfflineDefect>

    suspend fun saveOfflineDefect(
        defect: OfflineDefect,
    ): Result<Unit>

    suspend fun deleteOfflineDefect(defectId: String): Result<Unit>

    suspend fun updateOfflineDefect(defect: OfflineDefect): Result<Unit>
}