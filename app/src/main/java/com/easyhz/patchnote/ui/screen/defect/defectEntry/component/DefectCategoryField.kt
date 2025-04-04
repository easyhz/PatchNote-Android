package com.easyhz.patchnote.ui.screen.defect.defectEntry.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.getPostposition
import com.easyhz.patchnote.core.designSystem.component.textField.BaseTextField
import com.easyhz.patchnote.core.designSystem.component.textField.DropDownTextField
import com.easyhz.patchnote.core.model.category.CategoryType

@Composable
fun DefectCategoryField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    category: CategoryType,
    dropDownList: List<String>,
    onClickDropDownList: (String) -> Unit,
    onIconClick : (() -> Unit)? = null,
    onFocusChanged: (FocusState) -> Unit,
    onNext: () -> Unit,
) {
    Box(modifier = modifier) {
        when(category) {
            CategoryType.BUILDING, CategoryType.UNIT -> {
                BaseTextField(
                    containerModifier = Modifier.height(40.dp),
                    value = value.text,
                    onValueChange = {
                        onValueChange(TextFieldValue(it))
                    },
                    title = stringResource(id = category.nameId),
                    placeholder = stringResource(
                        id = R.string.defect_entry_placeholder,
                        getPostposition(stringResource(id = category.nameId))
                    ),
                    singleLine = true,
                    isFilled = false,
                    onFocusChanged = onFocusChanged,
                    onIconClick = onIconClick,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { onNext() }
                    )
                )
            }
            else -> {
                DropDownTextField(
                    containerModifier = Modifier.height(40.dp),
                    value = value,
                    onValueChange =  onValueChange,
                    dropDownList = dropDownList,
                    onClickDropDown = onClickDropDownList,
                    title = stringResource(id = category.nameId),
                    placeholder = stringResource(
                        id = R.string.defect_entry_placeholder,
                        getPostposition((stringResource(id = category.nameId)))
                    ),
                    singleLine = true,
                    isFilled = false,
                    onFocusChanged = onFocusChanged,
                    onIconClick = onIconClick,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { onNext() }
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefectCategoryFieldPreview() {
    DefectCategoryField(
        value = TextFieldValue(),
        onValueChange = {},
        category = CategoryType.BUILDING,
        dropDownList = emptyList(),
        onClickDropDownList = {},
        onFocusChanged = {},
        onNext = {},
        onIconClick= {}
    )

}