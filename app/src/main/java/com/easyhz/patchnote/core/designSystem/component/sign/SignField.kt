package com.easyhz.patchnote.core.designSystem.component.sign

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.designSystem.component.button.MainButton
import com.easyhz.patchnote.core.designSystem.component.textField.BaseTextField
import com.easyhz.patchnote.ui.theme.Bold20
import com.easyhz.patchnote.ui.theme.Caption
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Medium16

@Composable
fun SignField(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
     enabledButton: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = subTitle, style = Medium16, color = Caption)
            Text(text = title, style = Bold20, color = MainText)
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BaseTextField(
                containerModifier = Modifier.height(48.dp),
                value = value,
                onValueChange = onValueChange,
                title = null,
                placeholder = placeholder,
                singleLine = true,
                isFilled = false,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions
            )
            MainButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.sign_button),
                enabled = enabledButton,
                onClick = onClick
            )
        }

    }
}

@Preview
@Composable
private fun SignFieldPreview() {
    SignField(
        title = "전화번호를 입력해주세요",
        subTitle = "전화번호가 필요해요",
        value = "",
        placeholder = "전화번호 입력",
        onValueChange = {},
        enabledButton = false
    ) {

    }
}