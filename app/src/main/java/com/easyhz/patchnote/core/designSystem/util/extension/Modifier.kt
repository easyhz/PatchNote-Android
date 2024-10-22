package com.easyhz.patchnote.core.designSystem.util.extension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback

inline fun Modifier.noRippleClickable(
    interactionSource: MutableInteractionSource? = null,
    enabled: Boolean = true,
    crossinline onClick: () -> Unit,
): Modifier = composed {
    val haptic = LocalHapticFeedback.current
    clickable(
        indication = null,
        enabled = enabled,
        interactionSource = interactionSource ?: remember { MutableInteractionSource() }) {
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        onClick()
    }
}