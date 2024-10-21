package com.easyhz.patchnote.ui.navigation.home

import com.easyhz.patchnote.core.common.util.serializableType
import com.easyhz.patchnote.core.model.filter.FilterParam
import com.easyhz.patchnote.core.model.filter.FilterProgress
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class Home(
    val filterParam: FilterParam = FilterParam(
        indexFieldParam = linkedMapOf(
            "progress" to FilterProgress.REQUESTED.name
        )
    ),
) {
    companion object {
        val typeMap = mapOf(
            typeOf<FilterParam>() to serializableType<FilterParam>(),
        )
    }
}