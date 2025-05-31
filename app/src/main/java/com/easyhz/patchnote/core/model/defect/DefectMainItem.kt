package com.easyhz.patchnote.core.model.defect

import com.easyhz.patchnote.core.common.util.urlDecode
import kotlinx.serialization.Serializable

@Serializable
data class DefectMainItem(
    val id: String,
    val site: String,
    val building: String,
    val unit: String,
    val space: String,
    val part: String,
    val workType: String,
    val requesterName: String,
    val requestDate: String,
) {
    companion object {
        fun empty() = DefectMainItem(
            id = "",
            site = "",
            building = "",
            unit = "",
            space = "",
            part = "",
            workType = "",
            requesterName = "",
            requestDate = "",
        )
    }

    fun toDecode(): DefectMainItem = DefectMainItem(
        id = id,
        site = site.urlDecode(),
        building = building.urlDecode(),
        unit = unit.urlDecode(),
        space = space.urlDecode(),
        part = part.urlDecode(),
        workType = workType.urlDecode(),
        requesterName = requesterName.urlDecode(),
        requestDate = requestDate,
    )
}