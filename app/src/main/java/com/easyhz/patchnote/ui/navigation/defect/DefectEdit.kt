package com.easyhz.patchnote.ui.navigation.defect

import com.easyhz.patchnote.core.common.util.serializableType
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class DefectEdit(
    val defectItem: DefectItemArgs,
) {
    companion object {
        val typeMap = mapOf(
            typeOf<DefectItemArgs>() to serializableType<DefectItemArgs>()
        )
    }
}
