package com.easyhz.patchnote.core.common.util

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


internal fun String.urlEncode(): String {
    if(this.isBlank()) return this

    return URLEncoder.encode(
        this,
        StandardCharsets.UTF_8.toString()
    )
}

internal fun String.urlDecode(): String {
    if(this.isBlank()) return this

    return URLDecoder.decode(
        this,
        StandardCharsets.UTF_8.toString()
    )
}