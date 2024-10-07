package com.easyhz.patchnote.core.model.error

data class DialogMessage(
    val title: String,
    val message: String? = null,
    val action: DialogAction? = null
)

enum class DialogAction {
    RETRY,
    DISMISS,
    NAVIGATE_UP,
    CLEAR
}
