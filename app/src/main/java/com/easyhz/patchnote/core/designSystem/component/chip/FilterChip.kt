package com.easyhz.patchnote.core.designSystem.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.ui.theme.Medium16
import com.easyhz.patchnote.ui.theme.SubBackground

@Composable
fun FilterChip(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle,
    paddingValues: PaddingValues
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(SubBackground)
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = style,
            textAlign = TextAlign.Justify
        )
    }
}

@Preview
@Composable
private fun FilterChipPreview() {
    FilterChip(
        modifier = Modifier.height(28.dp),
        text = "101동 1201호",
        style = Medium16,
        paddingValues = PaddingValues(horizontal = 8.dp)
    )
}