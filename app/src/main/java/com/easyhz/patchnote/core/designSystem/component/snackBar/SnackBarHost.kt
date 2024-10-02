package com.easyhz.patchnote.core.designSystem.component.snackBar

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PatchNoteSnackBarHost(
    modifier: Modifier = Modifier,
    hostState: SnackbarHostState,
) {
    SnackbarHost(
        modifier = modifier,
        hostState = hostState
    ) {
        PatchNoteSnackBar(snackBarData = it)
    }
}