package com.easyhz.patchnote.core.database.defect.dao

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
    @Query("SELECT * FROM OFFLINE_DEFECT WHERE teamId = :teamId AND requesterId = :requesterId")
    fun findOfflineDefects(teamId: String, requesterId: String): List<OfflineDefect>

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
}