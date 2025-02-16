package com.easyhz.patchnote.data.datasource.local.defect

import androidx.paging.PagingSource
import com.easyhz.patchnote.core.database.defect.model.OfflineDefect

interface DefectLocalDataSource {
    fun findOfflineDefects(teamId: String, requesterId: String): PagingSource<Int, OfflineDefect>

    suspend fun saveOfflineDefect(
        defect: OfflineDefect,
    ): Result<Unit>

    suspend fun deleteOfflineDefects(defectId: String): Result<Unit>
}