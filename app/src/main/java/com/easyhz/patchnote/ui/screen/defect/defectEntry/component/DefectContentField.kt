package com.easyhz.patchnote.ui.screen.defect.defectEntry.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.designSystem.component.textField.BaseTextField
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.SemiBold18

@Composable
fun DefectContentField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier.height(32.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = stringResource(id = R.string.defect_entry_content),
                style = SemiBold18,
                color = MainText,
            )
        }
        BaseTextField(
            containerModifier = Modifier.heightIn(min = 40.dp),
            value = value,
            onValueChange = onValueChange,
            title = null,
            placeholder = stringResource(id = R.string.defect_entry_content_placeholder),
            singleLine = false,
            isFilled = false,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(
                onNext = { onNext() }
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DefectContentFieldPreview() {
    DefectContentField(
        value = "",
        onValueChange = {},
        onNext = {}
    )
}