package com.easyhz.patchnote.core.designSystem.component.dropDown

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Regular16

@Composable
fun BasicDropDown(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    dropDownList: List<String>,
    onSelected: (String) -> Unit
) {
    var isDropDownMenuExpanded by remember { mutableStateOf(isExpanded) }
    DropdownMenu(
        modifier = modifier
            .heightIn(max = 260.dp),
        shape = RoundedCornerShape(4.dp),
        offset = DpOffset(x = 0.dp, y = 4.dp),
        containerColor = MainBackground,
        expanded = isDropDownMenuExpanded,
        onDismissRequest = { isDropDownMenuExpanded = false }
    ) {
        dropDownList.forEach {
            DropdownMenuItem(
                text = {
                    Text(
                        text = it,
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
