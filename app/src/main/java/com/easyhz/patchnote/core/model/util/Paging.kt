package com.easyhz.patchnote.core.model.util

data class Paging<T> (
    val limit: Long,
    val offset: T?
)
