package com.easyhz.patchnote.core.model.filter

import kotlinx.serialization.Serializable

@Serializable
data class FilterParam(
    val searchFieldParam: LinkedHashMap<String, String>,
    val indexFieldParam: LinkedHashMap<String, String>,
)