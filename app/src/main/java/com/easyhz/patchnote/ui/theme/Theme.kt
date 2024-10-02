package com.easyhz.patchnote.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

val LocalSnackBarHostState = staticCompositionLocalOf { SnackbarHostState() }

@Composable
fun PatchNoteTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = MainBackground.toArgb()
            window.navigationBarColor = MainBackground.toArgb()
        }
    }
    MaterialTheme(
        content = content
    )
}