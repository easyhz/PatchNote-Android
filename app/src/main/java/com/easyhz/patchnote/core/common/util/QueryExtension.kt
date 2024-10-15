package com.easyhz.patchnote.core.common.util

import com.google.firebase.firestore.Query

fun Query.search(field: String, value: String?): Query {
    return if (value.isNullOrBlank()) this
    else whereArrayContains(field, value)
}
