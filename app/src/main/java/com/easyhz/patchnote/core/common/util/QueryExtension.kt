package com.easyhz.patchnote.core.common.util

import com.easyhz.patchnote.core.common.util.DateFormatUtil.convertStringToTimeStamp
import com.easyhz.patchnote.core.model.util.Paging
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

fun <T> Query.paging(paging: Paging<T>?): Query {
    return if (paging == null) this
    else if (paging.offset == null) limit(paging.limit)
    else limit(paging.limit).startAfter(paging.offset)
}
