package com.easyhz.patchnote.core.model.image

import androidx.annotation.StringRes
import com.easyhz.patchnote.R

data class DisplayImage(
    val site: String? = null,
    val buildingUnit: String? = null,
    val space: String? = null,
    val part: String? = null,
    val workType: String? = null,
    val request: String? = null,
    val completion: String? = null
) {

    fun toMap(): LinkedHashMap<DisplayImageType, String?> {
        return linkedMapOf(
            DisplayImageType.SITE to site,
            DisplayImageType.BUILDING_UNIT to buildingUnit,
            DisplayImageType.SPACE to space,
            DisplayImageType.PART to part,
            DisplayImageType.WORK_TYPE to workType,
            DisplayImageType.REQUEST to request,
            DisplayImageType.COMPLETION to completion,
        )
    }

}

enum class DisplayImageType(
    @StringRes val displayNameId: Int,
    val alias: String,
    val defaultOption: Boolean
) {
    SITE(
        displayNameId = R.string.image_site,
        alias = "site",
        defaultOption = true
    ), BUILDING_UNIT(
        displayNameId = R.string.image_building_unit,
        alias = "buildingUnit",
        defaultOption = true
    ), SPACE(
        displayNameId = R.string.image_space,
        alias = "space",
        defaultOption = true
    ), PART(
        displayNameId = R.string.image_part,
        alias = "part",
        defaultOption = false
    ), WORK_TYPE(
        displayNameId = R.string.image_work_type,
        alias = "workType",
        defaultOption = false
    ), REQUEST(
        displayNameId = R.string.image_work_request,
        alias = "request",
        defaultOption = true
    ), COMPLETION(
        displayNameId = R.string.image_work_completion,
        alias = "completion",
        defaultOption = false
    ),
}