package com.easyhz.patchnote.data.pagingsource.defect

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.easyhz.patchnote.data.model.defect.data.DefectData
import com.google.firebase.Timestamp

class DefectPagingSource(
    private val fetchData: suspend (Timestamp?, Int) -> Result<List<DefectData>>
): PagingSource<List<DefectData>, DefectData>() {
    override fun getRefreshKey(state: PagingState<List<DefectData>, DefectData>): List<DefectData>? {
        return null
    }

    override suspend fun load(params: LoadParams<List<DefectData>>): LoadResult<List<DefectData>, DefectData> {
        return try {
            val loadSize = params.loadSize
            val currentPage = params.key ?: fetchData(null, loadSize).getOrThrow()
            val offset = currentPage.lastOrNull()
                ?: return LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            val nextPage = fetchData(offset.requestDate, loadSize).getOrThrow()
            LoadResult.Page(
                data = currentPage,
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val PAGE_SIZE = 10
    }
}