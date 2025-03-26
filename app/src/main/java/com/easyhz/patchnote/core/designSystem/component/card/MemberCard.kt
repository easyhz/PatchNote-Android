package com.easyhz.patchnote.core.designSystem.component.card

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.ui.theme.Medium18

@Composable
fun MemberCard(
    modifier: Modifier = Modifier,
    name: String,
) {
    Row(
        modifier = modifier.fillMaxWidth().heightIn(min = 48.dp).padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = name,
            style = Medium18,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}


@Preview
@Composable
private fun Preview() {
    MemberCard(
        name = "Name"
    )
}