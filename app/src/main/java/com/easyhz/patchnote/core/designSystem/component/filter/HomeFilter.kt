package com.easyhz.patchnote.core.designSystem.component.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.designSystem.component.chip.FilterChip
import com.easyhz.patchnote.core.designSystem.util.extension.noRippleClickable
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Medium16

@Composable
fun HomeFilter(
    modifier: Modifier = Modifier,
    items: List<String>,
    onClick: () -> Unit
) {
    LazyRow(
        modifier = modifier.height(56.dp).noRippleClickable { onClick() },
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item {
            Icon(
                painter = painterResource(R.drawable.ic_filter),
                contentDescription = "icon_filter"
            )
        }
        if (items.isEmpty()) {
            item {
                FilterChip(
                    modifier = Modifier.height(28.dp),
                    text = stringResource(id = R.string.home_filter_empty),
                    style = Medium16.copy(color = MainText),
                    paddingValues = PaddingValues(horizontal = 8.dp)
                )
            }
        }
        items(items) {
            FilterChip(
                modifier = Modifier.height(28.dp),
                text = it,
                style = Medium16.copy(color = MainText),
                paddingValues = PaddingValues(horizontal = 8.dp)
            )
        }
    }
}