package com.easyhz.patchnote.core.designSystem.component.dropDown

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.designSystem.util.extension.noRippleClickable
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Regular16
import com.easyhz.patchnote.ui.theme.SemiBold18

@Composable
fun CategoryDropDown(
    modifier: Modifier = Modifier,
    selectedCategoryType: CategoryType,
    onSelected: (CategoryType) -> Unit
) {
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .heightIn(min = 36.dp)
                .noRippleClickable { isDropDownMenuExpanded = true },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(selectedCategoryType.nameId),
                style = SemiBold18,
                color = MainText
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_drop_down),
                contentDescription = "arrow_down",
                tint = MainText
            )
        }
        DropdownMenu(
            modifier = Modifier
                .wrapContentSize(),
            shape = RoundedCornerShape(4.dp),
            containerColor = MainBackground,
            expanded = isDropDownMenuExpanded,
            onDismissRequest = { isDropDownMenuExpanded = false }
        ) {
            CategoryType.entries.filter { it.enabledEntry }.forEach {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(it.nameId),
                            style = Regular16,
                            color = MainText
                        )
                    },
                    onClick = {
                        onSelected(it)
                        isDropDownMenuExpanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun CategoryDrownDownPreview() {
    CategoryDropDown(
        selectedCategoryType = CategoryType.SITE,
        onSelected = {}
    )
}