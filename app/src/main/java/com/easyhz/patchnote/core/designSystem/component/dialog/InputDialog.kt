package com.easyhz.patchnote.core.designSystem.component.dialog

import androidx.compose.foundation.background
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
import com.easyhz.patchnote.core.designSystem.component.textField.BaseTextField
import com.easyhz.patchnote.core.designSystem.util.dialog.BasicDialogButton
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.PlaceholderText
import com.easyhz.patchnote.ui.theme.Red
import com.easyhz.patchnote.ui.theme.SemiBold16
import com.easyhz.patchnote.ui.theme.SemiBold18
import com.easyhz.patchnote.ui.theme.SemiBold20
import com.easyhz.patchnote.ui.theme.SubBackground

@Composable
fun InputDialog(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String? = null,
    content: @Composable (() -> Unit)?,
    positiveButton: BasicDialogButton?,
    negativeButton: BasicDialogButton?,
    onDismissRequest: () -> Unit = negativeButton?.onClick ?: positiveButton?.onClick ?: { }
) {
    val innerPadding = 12.dp
    val buttonMinWidth = 132.dp
    val buttonSpacing = 12.dp
    val columSpace = if (subTitle == null) 32.dp else 24.dp

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = modifier
                .widthIn(max = innerPadding * 2 + buttonMinWidth * 2 + buttonSpacing + 32.dp)
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MainBackground)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(columSpace)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = title,
                    textAlign = TextAlign.Center,
                    style = SemiBold20,
                    color = MainText
                )
                subTitle?.let {
                    Text(
                        text = it,
                        textAlign = TextAlign.Center,
                        style = SemiBold16,
                        color = PlaceholderText
                    )
                }
            }
            content?.let {
                it()
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                negativeButton?.let {
                    DialogButton(
                        modifier = Modifier
                            .widthIn(min = buttonMinWidth)
                            .weight(1f),
                        dialogButton = it
                    )
                }
                positiveButton?.let {
                    DialogButton(
                        modifier = Modifier
                            .widthIn(min = buttonMinWidth)
                            .weight(1f),
                        dialogButton = it
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
//    device = "spec:shape=Normal,width=240,height=640, unit=dp, dpi= 480"
)
@Composable
private fun InputDialogPreview() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        InputDialog(
            title = "데이터 설정",
            subTitle = "접근하려면 관리자 권한이 필요합니다",
            content = {
                BaseTextField(
                    containerModifier = Modifier.height(40.dp),
                    value = "제목을입력해",
                    onValueChange = { },
                    title = null,
                    placeholder = "제목을 입력하세요",
                    singleLine = true,
                    isFilled = false,
                )
            },
            positiveButton = BasicDialogButton(
                text = "확인",
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