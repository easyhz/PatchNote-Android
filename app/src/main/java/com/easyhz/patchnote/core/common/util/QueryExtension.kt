package com.easyhz.patchnote.core.common.util

import com.easyhz.patchnote.core.common.util.DateFormatUtil.convertStringToTimeStamp
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
