package com.easyhz.patchnote.ui.navigation.defect

import com.easyhz.patchnote.core.common.util.serializableType
import com.easyhz.patchnote.core.model.defect.DefectMainItem
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class DefectCompletion(
    val defectMainItem: DefectMainItem
) {
    companion object {
        val typeMap = mapOf(
            typeOf<DefectMainItem>() to serializableType<DefectMainItem>()
        )
    }
}
