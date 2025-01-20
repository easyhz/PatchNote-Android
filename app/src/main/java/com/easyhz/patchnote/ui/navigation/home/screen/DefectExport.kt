package com.easyhz.patchnote.ui.navigation.home.screen

import com.easyhz.patchnote.core.common.util.serializableType
import com.easyhz.patchnote.core.model.filter.FilterParam
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class DefectExport(
    val filterParam: FilterParam
) {
    companion object {
        val typeMap = mapOf(
            typeOf<FilterParam>() to serializableType<FilterParam>(),
        )
    }
}