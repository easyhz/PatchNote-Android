package com.easyhz.patchnote.ui.navigation.offlineDefect.screen

import com.easyhz.patchnote.core.common.util.serializableType
import com.easyhz.patchnote.ui.navigation.defect.DefectItemArgs
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class OfflineDefectEdit(
    val defectItem: DefectItemArgs,
) {
    companion object {
        val typeMap = mapOf(
            typeOf<DefectItemArgs>() to serializableType<DefectItemArgs>()
        )
    }
}