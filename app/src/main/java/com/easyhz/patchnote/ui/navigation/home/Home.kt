package com.easyhz.patchnote.ui.navigation.home

import com.easyhz.patchnote.core.common.util.serializableType
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class Home(
    val searchParam: LinkedHashMap<String, String> = linkedMapOf(),
) {
    companion object {
        val typeMap = mapOf(
            typeOf<LinkedHashMap<String, String>>() to serializableType<LinkedHashMap<String, String>>(),
        )
    }
}