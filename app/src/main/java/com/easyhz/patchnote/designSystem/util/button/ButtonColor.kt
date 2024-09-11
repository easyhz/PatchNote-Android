package com.easyhz.patchnote.designSystem.util.button

import androidx.compose.ui.graphics.Color
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.Primary
import com.easyhz.patchnote.ui.theme.SubBackground
import com.easyhz.patchnote.ui.theme.SubText

data class ButtonColor(
    val containerColor: Color = Primary,
    val contentColor: Color = MainBackground,
    val disabledContainerColor: Color = SubBackground,
    val disabledContentColor: Color = SubText,
)
