package com.easyhz.patchnote.core.database.mock.defect

import com.easyhz.patchnote.core.database.defect.entity.OfflineDefectEntity
import com.easyhz.patchnote.core.database.defect.entity.OfflineDefectImageEntity

object MockDefect {

    fun offlineDefectEntity(
        defectId: String,
        teamId: String,
        requesterId: String,
    ) = OfflineDefectEntity(
        id = defectId,
        site = "Site",
        building = "Building",
        unit = "Unit",
        space = "Space",
        part = "Part",
        workType = "WorkType",
        beforeDescription = "description $defectId",
        requesterId = requesterId,
        requesterName = "RequesterName",
        requesterPhone = "RequesterPhone",
        teamId = teamId,
        creationTime = System.currentTimeMillis(),
    )

    fun offlineDefectImageEntityList(
        size: Int,
        defectId: String,
    ) = List(size) {
        offlineDefectImageEntity(
            defectId = defectId,
        )
    }

    fun offlineDefectImageEntity(
        defectId: String,
    ) = OfflineDefectImageEntity(
        defectId = defectId,
        url = "ImageUrl $defectId",
        height = 100,
        width = 100,
    )
}