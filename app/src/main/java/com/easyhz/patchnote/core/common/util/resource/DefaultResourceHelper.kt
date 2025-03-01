package com.easyhz.patchnote.core.common.util.resource

import android.content.Context
import com.easyhz.patchnote.core.common.util.resource.ResourceHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultResourceHelper @Inject constructor(
    @ApplicationContext private val context: Context
) : ResourceHelper {
    override fun getString(resId: Int): String {
        return context.getString(resId)
    }

    override fun getString(resId: Int, vararg formatArgs: Any?): String {
        return context.getString(resId, *formatArgs)
    }
}
