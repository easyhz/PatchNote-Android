package com.easyhz.patchnote.data.datasource.local.defect

import androidx.paging.PagingSource
import com.easyhz.patchnote.core.database.defect.dao.OfflineDefectDao
import com.easyhz.patchnote.core.database.defect.model.OfflineDefect
import javax.inject.Inject

class DefectLocalDataSourceImpl @Inject constructor(
    private val offlineDefectDao: OfflineDefectDao
): DefectLocalDataSource {
    override fun findOfflineDefects(
        teamId: String,
        requesterId: String
    ): PagingSource<Int, OfflineDefect> {
        return offlineDefectDao.findOfflineDefects(teamId, requesterId)
    }

    override suspend fun saveOfflineDefect(
        defect: OfflineDefect
    ) = runCatching {
        offlineDefectDao.saveOfflineDefect(defect.defect, defect.images)
    }

    override suspend fun deleteOfflineDefects(defectId: String) = runCatching {
        offlineDefectDao.deleteOfflineDefects(defectId)
    }
}