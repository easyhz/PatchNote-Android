package com.easyhz.patchnote.core.common.util.serializable

import com.squareup.moshi.Types
import java.lang.reflect.Type

interface SerializableHelper {
    fun <T> serialize(data: T, clazz: Class<T>): String
    fun <T> serialize(data: T, type: Type): String

    fun <T> deserialize(data: String?, clazz: Class<T>): T?
    fun <T> deserialize(data: String?, type: Type): T?
}

inline fun <reified T> SerializableHelper.serializeList(data: List<T>): String {
    val type = Types.newParameterizedType(List::class.java, T::class.java)
    return serialize(data, type)
}

inline fun <reified T> SerializableHelper.deserializeList(data: String): List<T>? {
    val type = Types.newParameterizedType(List::class.java, T::class.java)
    return deserialize(data, type)
}