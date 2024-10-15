package com.easyhz.patchnote.core.designSystem.component.textField

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.core.designSystem.util.textField.getTextFieldState
import com.easyhz.patchnote.ui.theme.Medium18

@Composable
fun BaseTextField(
    modifier: Modifier = Modifier,
    containerModifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    title: String?,
    placeholder: String,
    singleLine: Boolean,
    isFilled: Boolean,
    minLines: Int = 1,
    spacing: Dp = 12.dp,
    onIconClick: (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onFocusChanged: (FocusState) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val state = getTextFieldState(text = value, isFilled = isFilled)
    BasicTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { onFocusChanged(it) },
        textStyle = Medium18,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        minLines = minLines,
        visualTransformation = visualTransformation,
        decorationBox = { innerTextField ->
            TextFieldContainer(
                modifier = containerModifier,
                state = state,
                title = title,
                placeholder = placeholder,
                innerTextField = innerTextField,
                spacing = spacing,
                onIconClick = onIconClick,
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun BaseTextFieldPrev() {
    BaseTextField(
        containerModifier = Modifier.height(40.dp),
        value = "제목을입력해",
        onValueChange = { },
        title = "제목",
        placeholder = "제목을 입력하세요",
        singleLine = true,
        isFilled = false,
    )
}