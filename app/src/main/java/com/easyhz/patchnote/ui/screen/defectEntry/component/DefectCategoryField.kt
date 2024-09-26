package com.easyhz.patchnote.ui.screen.defectEntry.component

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
    onFocusChanged: (FocusState) -> Unit,
    onNext: () -> Unit,
) {
    val postposition = getPostposition(category)
    when(category) {
        CategoryType.BUILDING, CategoryType.UNIT -> {
            BaseTextField(
                containerModifier = modifier.height(40.dp),
                value = value.text,
                onValueChange = {
                    onValueChange(TextFieldValue(it))
                },
                title = stringResource(id = category.nameId),
                placeholder = stringResource(
                    id = R.string.defect_entry_placeholder,
                    (stringResource(id = category.nameId) + postposition)
                ),
                singleLine = true,
                isFilled = false,
                onFocusChanged = onFocusChanged,
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
                containerModifier = modifier.height(40.dp),
                value = value,
                onValueChange =  onValueChange,
                dropDownList = dropDownList,
                onClickDropDown = onClickDropDownList,
                title = stringResource(id = category.nameId),
                placeholder = stringResource(
                    id = R.string.defect_entry_placeholder,
                    (stringResource(id = category.nameId) + postposition)
                ),
                singleLine = true,
                isFilled = false,
                onFocusChanged = onFocusChanged,
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