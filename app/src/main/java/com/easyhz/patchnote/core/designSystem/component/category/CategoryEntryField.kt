package com.easyhz.patchnote.core.designSystem.component.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
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
import com.easyhz.patchnote.core.common.util.getPostposition
import com.easyhz.patchnote.core.designSystem.component.dropDown.CategoryDropDown
import com.easyhz.patchnote.core.designSystem.component.textField.BaseTextField
import com.easyhz.patchnote.core.designSystem.util.extension.noRippleClickable
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.ui.theme.Regular14
import com.easyhz.patchnote.ui.theme.SubText

@Composable
fun CategoryEntryField(
    modifier: Modifier = Modifier,
    selectedCategoryType: CategoryType,
    onSelected: (CategoryType) -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    onClickDelete: () -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryDropDown(
                selectedCategoryType = selectedCategoryType,
                onSelected = onSelected
            )
            Box(
                modifier = Modifier.heightIn(min = 36.dp).widthIn(min = 36.dp).noRippleClickable { onClickDelete()  },
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = stringResource(R.string.data_entry_delete),
                    style = Regular14,
                    color = SubText
                )
            }
        }

        BaseTextField(
            containerModifier = Modifier.height(48.dp),
            value = value,
            onValueChange = { onValueChange(it) },
            title = null,
            placeholder = getPlaceholderText(selectedCategoryType),
            singleLine = true,
            isFilled = false,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { onNext() }
            )
        )
    }
}

@Composable
private fun getPlaceholderText(selectedCategoryType: CategoryType): String {
    return stringResource(R.string.data_entry_placeholder, getPostposition(stringResource(selectedCategoryType.nameId)))
}

@Preview
@Composable
private fun CategoryEntryFieldPreview() {
    CategoryEntryField(
        selectedCategoryType = CategoryType.PART,
        onSelected = {},
        value = "",
        onValueChange = {},
        onClickDelete = {}
    ) {}

}