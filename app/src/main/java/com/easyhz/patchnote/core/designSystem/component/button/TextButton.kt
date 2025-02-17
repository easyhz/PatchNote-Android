package com.easyhz.patchnote.core.designSystem.component.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.ui.theme.SemiBold16

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    title: String,
    style: TextStyle = SemiBold16,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = style
        )
    }
}

@Preview
@Composable
private fun TextButtonPreview() {
    TextButton(
        title = "Button",
        style = SemiBold16.copy(
            textDecoration = TextDecoration.Underline
        ),
        onClick = { }
    )

}