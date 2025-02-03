package com.easyhz.patchnote.core.database.defect.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "OFFLINE_DEFECT"
)
data class OfflineDefectEntity(
    @PrimaryKey
    val id: String,
    val site: String,
    val building: String,
    val unit: String,
    val space: String,
    val part: String,
    val workType: String,
    val beforeDescription: String,
    val requesterId: String,
    val requesterName: String,
    val requesterPhone: String,
    val teamId: String,
    val creationTime: Long = System.currentTimeMillis(),
)
