package com.easyhz.patchnote.core.common.util

import com.easyhz.patchnote.core.common.constant.Field.COMPLETION_DATE_STR
import com.easyhz.patchnote.core.common.constant.Field.PROGRESS
import com.easyhz.patchnote.core.common.constant.Field.REQUEST_DATE
import com.easyhz.patchnote.core.common.constant.Field.WORKER_NAME
import com.easyhz.patchnote.core.common.util.DateFormatUtil.convertStringToTimeStamp
import com.easyhz.patchnote.core.model.filter.IndexField
import com.google.firebase.firestore.Query

fun Query.search(field: String, value: String?): Query {
    return if (value.isNullOrBlank()) this
    else whereArrayContains(field, value)
}

fun Query.indexSearch(field: String, value: String?): Query {
    return if (value.isNullOrBlank()) this
    else whereEqualTo(field, value)
}

fun Query.indexSearchDate(field: String, value: String?): Query {
    return if (value.isNullOrBlank()) this
    else whereGreaterThanOrEqualTo(
        field,
        convertStringToTimeStamp(value)
    ).whereLessThanOrEqualTo(
        field,
        convertStringToTimeStamp(value, 1)
    )
}
