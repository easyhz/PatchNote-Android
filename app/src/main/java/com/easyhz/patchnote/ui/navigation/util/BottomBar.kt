package com.easyhz.patchnote.ui.navigation.util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.core.designSystem.util.extension.dropShadow
import com.easyhz.patchnote.ui.theme.BottomBarBackground
import com.easyhz.patchnote.ui.theme.Medium10
import com.easyhz.patchnote.ui.theme.Primary
import com.easyhz.patchnote.ui.theme.SubText

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    current: BottomMenuTabs?,
    onSelected: (BottomMenuTabs) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 49.dp)
            .dropShadow(
                shape = RectangleShape,
                color = Color.Black.copy(alpha = 0.3f),
                offsetY = (-0.5).dp,
            )
            .background(BottomBarBackground)

        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomMenuTabs.entries.forEach { item ->
            BottomBarItem(
                modifier = Modifier.weight(1f),
                painter = painterResource(id = item.iconId),
                label = stringResource(id = item.label),
                selected = item == current,
                onClick = { onSelected(item) },
            )
        }
    }
}


@Composable
internal fun BottomBarItem(
    modifier: Modifier = Modifier,
    painter: Painter,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(bounded = true, radius = 52.dp, color = SubText),
                onClick = onClick
            )
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painter,
            tint = if (selected) Primary else SubText,
            contentDescription = label,
        )

        Text(
            text = label,
            style = Medium10,
            color = if (selected) Primary else SubText
        )
    }
}


@Preview
@Composable
private fun BottomBarPreview() {
    BottomBar(current = BottomMenuTabs.HOME, onSelected = {})

}