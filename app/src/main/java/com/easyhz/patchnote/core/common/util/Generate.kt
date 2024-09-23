package com.easyhz.patchnote.core.common.util

import java.util.UUID

object Generate {
    fun randomUUID() = UUID.randomUUID().toString().uppercase()
}