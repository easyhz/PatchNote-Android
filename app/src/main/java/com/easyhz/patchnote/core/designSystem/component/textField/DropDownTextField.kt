package com.easyhz.patchnote.core.designSystem.component.textField

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.core.designSystem.util.textField.getTextFieldState
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Medium18

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownTextField(
    modifier: Modifier = Modifier,
    containerModifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    dropDownList: List<String>,
    onClickDropDown: (String) -> Unit,
    title: String?,
    placeholder: String,
    singleLine: Boolean,
    isFilled: Boolean,
    minLines: Int = 1,
    spacing: Dp = 12.dp,
    onFocusChanged: (FocusState) -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    var isExpanded by remember { mutableStateOf(false) }
    val state = getTextFieldState(text = value.text, isFilled = isFilled)
    Row(
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        title?.let {
            TextFieldContainerTitle(
                title = title,
            )
        }
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = {
                isExpanded = !isExpanded
            }
        ) {
            BasicTextField(
                value = value,
                onValueChange = { onValueChange(it) },
                modifier = modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        onFocusChanged(it)
                        isExpanded = it.isFocused
                    }
                    .menuAnchor(MenuAnchorType.PrimaryEditable),
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
                        title = null,
                        placeholder = placeholder,
                        innerTextField = innerTextField,
                        spacing = spacing,
                    )
                }
            )
            if (dropDownList.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = isExpanded, onDismissRequest = { isExpanded = false },
                    shape = RoundedCornerShape(4.dp),
                    containerColor = MainBackground,
                    shadowElevation = 4.dp
                ) {
                    dropDownList.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(text = option, color = MainText)
                            },
                            onClick = {
                                onClickDropDown(option)
                                isExpanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DropDownTextFieldPreview() {
    DropDownTextField(
        containerModifier = Modifier.height(40.dp),
        value = TextFieldValue("제목을입력해"),
        onValueChange = { },
        dropDownList = listOf("Option1", "Option2", "Option3"),
        onClickDropDown = { },
        title = "제목",
        placeholder = "제목을 입력하세요",
        singleLine = true,
        isFilled = false,
    )
}