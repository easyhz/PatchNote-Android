package com.easyhz.patchnote.core.common.util.serializable

import com.easyhz.patchnote.core.common.util.log.Logger
import com.squareup.moshi.Moshi
import javax.inject.Inject

internal class MoshiSerializableHelper @Inject constructor(
    private val moshi: Moshi,
    private val logger: Logger
): SerializableHelper {
    private val tag = MoshiSerializableHelper::class.java.simpleName

    override fun <T> serialize(data: T, clazz: Class<T>): String {
        return moshi.adapter(clazz).toJson(data)
    }

    override fun <T> deserialize(data: String?, clazz: Class<T>): T? {
        try {
            return if (data.isNullOrBlank()) null
            else moshi.adapter(clazz).fromJson(data)!!
        } catch (e: Exception) {
            logger.e(tag, "deserialize", e)
            return null
        }
    }
}