package com.easyhz.patchnote.core.designSystem.util.extension

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

fun Modifier.border(type: BorderType, color: Color, width: Dp): Modifier = this.drawBehind {
    val stroke = width.toPx()
    val half = stroke / 2

    val (start, end) = when (type) {
        BorderType.TOP -> Offset(0f, half) to Offset(size.width, half)
        BorderType.BOTTOM -> Offset(0f, size.height - half) to Offset(size.width, size.height - half)
        BorderType.LEFT -> Offset(half, 0f) to Offset(half, size.height)
        BorderType.RIGHT -> Offset(size.width - half, 0f) to Offset(size.width - half, size.height)
    }

    drawLine(
        color = color,
        start = start,
        end = end,
        strokeWidth = stroke
    )
}

enum class BorderType {
    TOP,
    BOTTOM,
    LEFT,
    RIGHT,
}