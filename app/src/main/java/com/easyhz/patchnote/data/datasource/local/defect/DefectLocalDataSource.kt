package com.easyhz.patchnote.data.datasource.local.defect

import com.easyhz.patchnote.core.database.defect.model.OfflineDefect

interface DefectLocalDataSource {
    suspend fun findOfflineDefects(teamId: String, requesterId: String): List<OfflineDefect>

    suspend fun saveOfflineDefect(
        defect: OfflineDefect,
    ): Result<Unit>

    suspend fun deleteOfflineDefects(defectId: String): Result<Unit>
}