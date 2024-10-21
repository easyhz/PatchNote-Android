package com.easyhz.patchnote.core.designSystem.component.bottomSheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Medium14

interface BottomSheetType {
    val titleId: Int
    val iconId: Int
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ListBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    items: Array<T>,
    onDismissRequest: () -> Unit,
    onClick: (T) -> Unit
) where T: Enum<T>, T: BottomSheetType {
    BasicBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            items.forEach { item ->
                BottomSheetItem(
                    icon  = painterResource(id = item.iconId),
                    title = stringResource(id = item.titleId),
                    onClick = {
                        onClick(item)
                        onDismissRequest()
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomSheetItem(
    modifier: Modifier = Modifier,
    icon: Painter,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = icon,
            contentDescription = null,
            tint = MainText
        )
        Text(
            text = title,
            style = Medium14,
            color = MainText
        )
    }
}