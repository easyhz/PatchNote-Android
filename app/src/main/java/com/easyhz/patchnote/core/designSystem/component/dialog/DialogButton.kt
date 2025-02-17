package com.easyhz.patchnote.core.designSystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.core.designSystem.util.dialog.BasicDialogButton
import com.easyhz.patchnote.ui.theme.Red
import com.easyhz.patchnote.ui.theme.SemiBold18


@Composable
fun DialogButton(
    modifier: Modifier = Modifier,
    dialogButton: BasicDialogButton,
) {
    Box(
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(dialogButton.backgroundColor)
            .clickable { dialogButton.onClick() }
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dialogButton.text,
            style = dialogButton.style
        )
    }
}

@Preview
@Composable
private fun DialogButtonPreview() {
    DialogButton(
        dialogButton = BasicDialogButton(
            text = "확인",
            backgroundColor = Red,
            style = SemiBold18.copy(color = Color.White),
            onClick = {}
        )
    )
}