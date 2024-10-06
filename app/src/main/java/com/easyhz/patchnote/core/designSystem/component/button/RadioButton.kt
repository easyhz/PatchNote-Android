package com.easyhz.patchnote.core.designSystem.component.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.core.designSystem.util.button.RadioInterface
import com.easyhz.patchnote.core.designSystem.util.extension.noRippleClickable
import com.easyhz.patchnote.core.model.filter.FilterProgress
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Medium16
import com.easyhz.patchnote.ui.theme.Primary
import com.easyhz.patchnote.ui.theme.UnselectedColor

@Composable
fun <T> BasicRadioButton(
    modifier: Modifier = Modifier,
    items: List<T>,
    selected: Int,
    onClick: (Int) -> Unit,
) where T: RadioInterface {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items.forEachIndexed { index, info ->
            Row(
                modifier = Modifier.noRippleClickable { onClick(index) },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                PatchNoteRadioButton(
                    selected = index == selected,
                    onClick = { onClick(index) },
                )
                Text(
                    text = stringResource(id = info.titleId),
                    style = Medium16,
                    color = MainText
                )
            }
        }
    }
}

@Composable
fun PatchNoteRadioButton(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val border =  if (selected) 5.5.dp else 1.5.dp
    val borderColor =
        animateColorAsState(if (selected) Primary else UnselectedColor, label = "borderColor")
    Box(
        modifier = modifier
            .size(20.dp)
            .clip(CircleShape)
            .border(width = border, color = borderColor.value, shape = CircleShape)
            .clickable { onClick() },
    )
}

@Preview(showBackground = true)
@Composable
private fun BasicRadioButtonPreview() {
    var state by remember { mutableIntStateOf(0) }
    BasicRadioButton(
        items = FilterProgress.entries,
        selected = state,
        onClick = {
            state = it
        }
    )
}