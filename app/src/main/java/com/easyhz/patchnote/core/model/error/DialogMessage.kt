package com.easyhz.patchnote.core.model.error

import com.easyhz.patchnote.core.designSystem.util.dialog.BasicDialogButton

data class DialogMessage(
    val title: String,
    val message: String? = null,
    val action: DialogAction? = null,
    val positiveButton: BasicDialogButton? = null,
    val negativeButton: BasicDialogButton? = null,
)

enum class DialogAction {
    RETRY,
    DISMISS,
    NAVIGATE_UP,
    CLEAR,
    CUSTOM_ACTION
}
