package com.easyhz.patchnote.core.designSystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.designSystem.component.button.TextButton
import com.easyhz.patchnote.core.designSystem.util.dialog.BasicDialogButton
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Primary
import com.easyhz.patchnote.ui.theme.SemiBold16
import com.easyhz.patchnote.ui.theme.SemiBold18
import com.easyhz.patchnote.ui.theme.SemiBold20
import com.easyhz.patchnote.ui.theme.SubBackground
import com.easyhz.patchnote.ui.theme.SubText

@Composable
fun DefectEntryDialog(
    modifier: Modifier = Modifier,
    onClickSave: () -> Unit,
    onClickOfflineSave: () -> Unit,
    onDismissRequest: () -> Unit
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
                .clip(RoundedCornerShape(8.dp))
                .background(MainBackground)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = stringResource(R.string.defect_entry_dialog_title),
                textAlign = TextAlign.Center,
                style = SemiBold20,
                color = MainText
            )
            Column (
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                DialogButton(
                    modifier = Modifier.fillMaxWidth(),
                    dialogButton = BasicDialogButton(
                        text = stringResource(R.string.defect_entry_dialog_save),
                        backgroundColor = Primary,
                        style = SemiBold18.copy(color = MainBackground),
                        onClick = onClickSave
                    )
                )
                DialogButton(
                    modifier = Modifier.fillMaxWidth(),
                    dialogButton = BasicDialogButton(
                        text = stringResource(R.string.defect_entry_dialog_offline_save),
                        backgroundColor = SubBackground,
                        style = SemiBold18.copy(color = MainText),
                        onClick = onClickOfflineSave
                    )
                )
                TextButton(
                    modifier = Modifier.heightIn(min = 32.dp).fillMaxWidth(),
                    title = stringResource(R.string.defect_entry_dialog_cancel),
                    style = SemiBold16.copy(
                        color = SubText,
                        textDecoration = TextDecoration.Underline
                    ),
                    onClick = onDismissRequest
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
private fun DefectEntryDialogPreview() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        DefectEntryDialog(
            onClickSave = {},
            onClickOfflineSave = {},
            onDismissRequest = {}
        )
    }
}