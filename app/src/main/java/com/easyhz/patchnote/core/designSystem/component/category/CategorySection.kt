package com.easyhz.patchnote.core.designSystem.component.category

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.model.category.Category
import com.easyhz.patchnote.ui.theme.Regular14
import com.easyhz.patchnote.ui.theme.SemiBold16
import com.easyhz.patchnote.ui.theme.SemiBold18
import com.easyhz.patchnote.ui.theme.SubBackground
import com.easyhz.patchnote.ui.theme.SubText

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.categorySection(
    category: Category,
    onClick: (index: Int) -> Unit
) {
    stickyHeader {
        Box(
            modifier = Modifier.fillMaxWidth().height(32.dp).background(SubBackground).padding(horizontal = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = stringResource(id = category.type.nameId),
                style = SemiBold18
            )
        }
    }
    itemsIndexed(category.values) { index, item ->
        Row(
            modifier = Modifier.fillMaxWidth().clickable { onClick(index) }.padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f).padding(vertical = 12.dp),
                text = item,
                style = SemiBold16
            )
            Text(
                text = stringResource(R.string.data_management_delete),
                style = Regular14,
                color = SubText
            )
        }
    }
}

@Preview
@Composable
private fun CategorySectionPreview() {
    LazyColumn {
        categorySection(category = Category.Site(listOf("site1", "site2", "site3"))) {

        }
    }

}