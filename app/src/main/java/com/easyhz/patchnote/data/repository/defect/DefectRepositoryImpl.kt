package com.easyhz.patchnote.data.repository.defect

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.model.defect.DefectCompletion
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.defect.EntryDefect
import com.easyhz.patchnote.core.model.defect.OfflineDefect
import com.easyhz.patchnote.core.model.filter.FilterParam
import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.core.model.util.Paging
import com.easyhz.patchnote.data.datasource.local.defect.DefectLocalDataSource
import com.easyhz.patchnote.data.datasource.remote.defect.DefectDataSource
import com.easyhz.patchnote.data.mapper.defect.toData
import com.easyhz.patchnote.data.mapper.defect.toEntity
import com.easyhz.patchnote.data.mapper.defect.toExportDefect
import com.easyhz.patchnote.data.mapper.defect.toDefectItem
import com.easyhz.patchnote.data.mapper.defect.toModel
import com.easyhz.patchnote.data.pagingsource.defect.DefectPagingSource
import com.easyhz.patchnote.data.pagingsource.defect.DefectPagingSource.Companion.PAGE_SIZE
import com.easyhz.patchnote.data.util.ExportUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class DefectRepositoryImpl @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val defectDataSource: DefectDataSource,
    private val exportUtil: ExportUtil,
    private val defectLocalDataSource: DefectLocalDataSource,
) : DefectRepository {
    override suspend fun createDefect(param: EntryDefect): Result<Unit> {
        return defectDataSource.createDefect(param.toData())
    }

    override suspend fun fetchDefects(
        filterParam: FilterParam,
        user: User
    ): Result<List<DefectItem>> {
        val searchFieldParam = filterParam.searchFieldParam.entries.joinToString("||") {
            "${it.key}=${it.value}"
        }
        val indexSearchField = filterParam.toIndexField()
        return defectDataSource.fetchDefects(searchFieldParam, indexSearchField, user, null)
            .map { it.map { defectData -> defectData.toDefectItem() } }
    }

    override fun getDefectsPagingSource(
        filterParam: FilterParam,
        user: User
    ): Flow<PagingData<DefectItem>> {
        val searchFieldParam = filterParam.searchFieldParam.entries.joinToString("||") {
            "${it.key}=${it.value}"
        }
        val indexSearchField = filterParam.toIndexField()
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = PAGE_SIZE)
        ) {
            DefectPagingSource { page, loadSize ->
                defectDataSource.fetchDefects(
                    search = searchFieldParam,
                    index = indexSearchField,
                    user = user,
                    paging = Paging(
                        limit = loadSize.toLong(),
                        offset = page
                    )
                )
            }
        }.flow.map { it.map { defectData -> defectData.toDefectItem() } }.flowOn(dispatcher)
    }

    override suspend fun fetchDefect(id: String): Result<DefectItem> {
        return defectDataSource.fetchDefect(id).map { it.toDefectItem() }
    }

    override suspend fun updateDefectCompletion(param: DefectCompletion): Result<Unit> {
        return defectDataSource.updateDefectCompletion(param.id, param.toData())
    }

    override suspend fun deleteDefect(id: String): Result<Unit> {
        return defectDataSource.deleteDefect(id)
    }

    override suspend fun exportDefects(defects: List<DefectItem>): Result<File> = runCatching {
        exportUtil.exportDefects(defects.map { it.toExportDefect() })
    }

    override suspend fun saveOfflineDefect(defect: EntryDefect): Result<Unit> {
        return defectLocalDataSource.saveOfflineDefect(defect.toEntity())
    }

    override suspend fun getOfflineDefectsPagingSource(
        teamId: String,
        requesterId: String
    ): Flow<PagingData<DefectItem>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = PAGE_SIZE)
        ) {
            defectLocalDataSource.findOfflineDefectsPagingSource(teamId, requesterId)
        }.flow
        .map {
            it.map { offlineDefect -> offlineDefect.toDefectItem() }
        }.flowOn(dispatcher)
    }

    override fun findOfflineDefects(teamId: String, requesterId: String): List<OfflineDefect> {
        return defectLocalDataSource.findOfflineDefects(teamId, requesterId).map { it.toModel() }
    }

    override suspend fun findOfflineDefect(defectId: String): Result<DefectItem> = withContext(dispatcher) {
        return@withContext defectLocalDataSource.findOfflineDefect(defectId).map { it.toDefectItem() }
    }

    override suspend fun deleteOfflineDefect(defectId: String): Result<Unit> {
        return defectLocalDataSource.deleteOfflineDefect(defectId)
    }

    override suspend fun updateOfflineDefect(defect: EntryDefect): Result<Unit> {
        return defectLocalDataSource.updateOfflineDefect(defect.toEntity())
    }
}