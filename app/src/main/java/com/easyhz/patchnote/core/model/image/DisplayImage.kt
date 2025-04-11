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
) {
    SITE(
        displayNameId = R.string.image_site,
    ), BUILDING_UNIT(
        displayNameId = R.string.image_building_unit,
    ), SPACE(
        displayNameId = R.string.image_space,
    ), PART(
        displayNameId = R.string.image_part,
    ), WORK_TYPE(
        displayNameId = R.string.image_work_type,
    ), REQUEST(
        displayNameId = R.string.image_work_request,
    ), COMPLETION(
        displayNameId = R.string.image_work_completion,
    ),
}