package com.easyhz.patchnote.core.database.defect.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.easyhz.patchnote.core.database.defect.entity.OfflineDefectEntity
import com.easyhz.patchnote.core.database.defect.entity.OfflineDefectImageEntity
import com.easyhz.patchnote.core.database.defect.model.OfflineDefect

/**
 * 하자 임시저장
 *
 * 1. 조회
 * 2. 저장
 * 3. 삭제
 */
@Dao
interface OfflineDefectDao {

    /**
     * 하자 임시저장 조회
     */
    @Transaction
    @Query("""
        SELECT * 
        FROM OFFLINE_DEFECT 
        WHERE teamId = :teamId 
            AND requesterId = :requesterId
        ORDER BY creationTime DESC
    """)
    fun findOfflineDefectsPagingSource(teamId: String, requesterId: String): PagingSource<Int, OfflineDefect>

    @Transaction
    @Query("""
        SELECT * 
        FROM OFFLINE_DEFECT 
        WHERE teamId = :teamId 
            AND requesterId = :requesterId
        ORDER BY creationTime DESC
    """)
    fun findOfflineDefects(teamId: String, requesterId: String): List<OfflineDefect>

    @Query("SELECT * FROM OFFLINE_DEFECT WHERE id = :defectId")
    fun findOfflineDefect(defectId: String): OfflineDefect

    /**
     * 하자 임시저장 저장
     */
    @Transaction
    suspend fun saveOfflineDefect(
        defect: OfflineDefectEntity,
        images: List<OfflineDefectImageEntity>
    ) {
        insertOfflineDefect(defect)
        insertImages(*images.toTypedArray())
    }


    /* 하자 저장 */
    @Upsert
    suspend fun insertOfflineDefect(defect: OfflineDefectEntity)

    /* 이미지 저장 */
    @Upsert
    suspend fun insertImages(vararg images: OfflineDefectImageEntity)


    /**
     * 하자 임시저장 삭제
     */
    @Query("DELETE FROM OFFLINE_DEFECT WHERE id = :defectId")
    suspend fun deleteOfflineDefects(defectId: String)

    /**
     * 하자 이미지 삭제
     */
    @Query("DELETE FROM OFFLINE_DEFECT_IMAGE WHERE defectId = :defectId")
    suspend fun deleteOfflineDefectImages(defectId: String)

    /**
     * 임시 하자 사진 삭제 후 업데이트
     */
    @Transaction
    suspend fun updateOfflineDefect(
        defect: OfflineDefectEntity,
        images: List<OfflineDefectImageEntity>
    ) {
        deleteOfflineDefectImages(defect.id)
        saveOfflineDefect(defect, images)
    }
}