package com.easyhz.patchnote.core.model.defect

import androidx.annotation.StringRes

enum class ExportDefectHeader(
    @StringRes val resId: Int
) {
    ID(com.easyhz.patchnote.R.string.defect_id),
    SITE(com.easyhz.patchnote.R.string.defect_site),
    BUILDING(com.easyhz.patchnote.R.string.defect_building),
    UNIT(com.easyhz.patchnote.R.string.defect_unit),
    SPACE(com.easyhz.patchnote.R.string.defect_space),
    PART(com.easyhz.patchnote.R.string.defect_part),
    WORK_TYPE(com.easyhz.patchnote.R.string.defect_work_type),
    PROGRESS(com.easyhz.patchnote.R.string.defect_progress),
    REQUESTER_NAME(com.easyhz.patchnote.R.string.defect_requester_name),
    REQUESTER_PHONE(com.easyhz.patchnote.R.string.defect_requester_phone),
    REQUEST_DATE(com.easyhz.patchnote.R.string.defect_request_date),
    BEFORE_DESCRIPTION(com.easyhz.patchnote.R.string.defect_before_description),
    WORKER_NAME(com.easyhz.patchnote.R.string.defect_worker_name),
    WORKER_PHONE(com.easyhz.patchnote.R.string.defect_worker_phone),
    COMPLETION_DATE(com.easyhz.patchnote.R.string.defect_completion_date),
    AFTER_DESCRIPTION(com.easyhz.patchnote.R.string.defect_after_description),
    THUMBNAIL(com.easyhz.patchnote.R.string.defect_thumbnail)
}