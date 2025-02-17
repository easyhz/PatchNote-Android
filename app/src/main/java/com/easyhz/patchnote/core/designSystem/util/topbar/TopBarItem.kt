package com.easyhz.patchnote.core.designSystem.util.topbar

import androidx.compose.ui.graphics.painter.Painter

data class TopBarItem(
    val painter: Painter,
    val onClick: () -> Unit
)
