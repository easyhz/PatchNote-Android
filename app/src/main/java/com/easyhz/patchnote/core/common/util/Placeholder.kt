package com.easyhz.patchnote.core.common.util

fun getPostposition(word: String): String {
    if (word.isEmpty()) return word
    val lastChar = word.last()
    if (lastChar in '\uAC00'..'\uD7A3') {
        val code = lastChar.code - 0xAC00
        val last = code % 28
        val postposition = if (last == 0) "를" else "을"
        return "$word$postposition"
    }
    return word
}