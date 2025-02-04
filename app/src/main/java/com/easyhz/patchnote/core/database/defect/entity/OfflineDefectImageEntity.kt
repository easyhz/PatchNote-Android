package com.easyhz.patchnote.core.database.defect.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "OFFLINE_DEFECT_IMAGE",
    foreignKeys = [
        ForeignKey(
            entity = OfflineDefectEntity::class,
            parentColumns = ["id"],
            childColumns = ["defectId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class OfflineDefectImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val defectId: String,
    val url: String,
    val height: Long,
    val width: Long,
)
