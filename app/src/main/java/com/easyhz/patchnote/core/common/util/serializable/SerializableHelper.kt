package com.easyhz.patchnote.core.common.util.serializable


interface SerializableHelper {
    fun <T> serialize(data: T,  clazz: Class<T>): String
    fun <T> deserialize(data: String?, clazz: Class<T>): T?
}