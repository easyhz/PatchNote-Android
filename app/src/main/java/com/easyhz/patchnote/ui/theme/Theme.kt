package com.easyhz.patchnote.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun PatchNoteTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content
    )
}