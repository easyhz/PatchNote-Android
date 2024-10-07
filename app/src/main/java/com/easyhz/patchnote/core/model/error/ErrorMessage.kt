package com.easyhz.patchnote.core.model.error

data class ErrorMessage(
    val title: String,
    val message: String? = null,
    val action: ErrorAction? = null
)

enum class ErrorAction {
    RETRY,
    DISMISS,
    NAVIGATE_UP
}
