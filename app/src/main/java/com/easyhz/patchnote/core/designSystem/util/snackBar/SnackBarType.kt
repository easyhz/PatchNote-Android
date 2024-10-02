package com.easyhz.patchnote.core.designSystem.util.snackBar

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class SnackBarType {
    data object Default: SnackBarType()
    data object ButtonTop: SnackBarType()
    data class CustomTop(val bottom: Dp): SnackBarType()
}

fun Modifier.snackBarPadding(snackBarType: SnackBarType): Modifier {
    val bottomPadding = when (snackBarType) {
        is SnackBarType.Default -> 12.dp
        is SnackBarType.ButtonTop -> 84.dp
        is SnackBarType.CustomTop -> snackBarType.bottom
    }

    return padding(horizontal = 20.dp).padding(bottom = bottomPadding)
}