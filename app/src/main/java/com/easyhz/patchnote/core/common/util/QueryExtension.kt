package com.easyhz.patchnote.core.common.util

import com.google.firebase.firestore.Query

fun Query.search(field: String, value: List<String>?): Query {
    return if (value.isNullOrEmpty()) this
    else whereArrayContains(field, value)
}
