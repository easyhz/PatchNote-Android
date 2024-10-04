package com.easyhz.patchnote.core.designSystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.easyhz.patchnote.core.designSystem.util.dialog.BasicDialogButton
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.PlaceholderText
import com.easyhz.patchnote.ui.theme.Red
import com.easyhz.patchnote.ui.theme.SemiBold16
import com.easyhz.patchnote.ui.theme.SemiBold18
import com.easyhz.patchnote.ui.theme.SemiBold20
import com.easyhz.patchnote.ui.theme.SubBackground

@Composable
fun BasicDialog(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    positiveButton: BasicDialogButton,
    negativeButton: BasicDialogButton,
    onDismissRequest: () -> Unit = negativeButton.onClick
) {
    val innerPadding = 12.dp
    val buttonMinWidth = 132.dp
    val buttonSpacing = 12.dp

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = modifier
                .widthIn(max = innerPadding * 2 + buttonMinWidth * 2 + buttonSpacing + 32.dp)
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = title,
                textAlign = TextAlign.Center,
                style = SemiBold20,
                color = MainText
            )
            Text(
                text = content,
                textAlign = TextAlign.Center,
                style = SemiBold16,
                color = PlaceholderText
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                DialogButton(
                    modifier = Modifier.widthIn(min = buttonMinWidth).weight(1f),
                    dialogButton = negativeButton
                )
                DialogButton(
                    modifier = Modifier.widthIn(min = buttonMinWidth).weight(1f),
                    dialogButton = positiveButton
                )
            }
        }
    }
}

@Composable
private fun DialogButton(
    modifier: Modifier = Modifier,
    dialogButton: BasicDialogButton,
) {
    Box(
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(dialogButton.backgroundColor)
            .clickable { dialogButton.onClick() },
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
@Preview(
    showBackground = true,
//    device = "spec:shape=Normal,width=240,height=640, unit=dp, dpi= 480"
)
@Composable
fun BasicDialogPreview() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BasicDialog(
            title = "힐스테이트 양주옥정 파티오포레 삭제할까요??",
            content = "삭제된 데이터는 복구할 수 없습니다.",
            positiveButton = BasicDialogButton(
                text = "삭제",
                style = SemiBold18.copy(color = Color.White),
                backgroundColor = Red,
                onClick = {}
            ),
            negativeButton = BasicDialogButton(
                text = "취소",
                backgroundColor = SubBackground,
                onClick = {}
            )
        )
    }
}