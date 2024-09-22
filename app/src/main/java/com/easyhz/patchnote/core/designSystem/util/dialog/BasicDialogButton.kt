package com.easyhz.patchnote.core.designSystem.util.dialog

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.easyhz.patchnote.ui.theme.SemiBold18

data class BasicDialogButton(
    val text: String,
    val backgroundColor: Color,
    val style: TextStyle = SemiBold18,
    val onClick: () -> Unit
)