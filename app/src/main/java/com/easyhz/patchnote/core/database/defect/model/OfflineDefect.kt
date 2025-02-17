package com.easyhz.patchnote.core.database.defect.model

import androidx.room.Embedded
import androidx.room.Relation
import com.easyhz.patchnote.core.database.defect.entity.OfflineDefectImageEntity
import com.easyhz.patchnote.core.database.defect.entity.OfflineDefectEntity

data class OfflineDefect(
    @Embedded val defect: OfflineDefectEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "defectId"
    )
    val images: List<OfflineDefectImageEntity>
)
