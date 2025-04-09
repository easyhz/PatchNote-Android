package com.easyhz.patchnote.core.designSystem.component.button

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.R
import com.easyhz.patchnote.ui.theme.SubText

@Composable
fun CheckBoxButton(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier.size(32.dp).clickable(
            interactionSource = interactionSource,
            indication = ripple(bounded = true, radius = 52.dp, color = SubText),
        ) {
            onCheckedChange(!checked)
        },
        contentAlignment = Alignment.Center,
    ) {
        Crossfade(
            targetState = checked,
            label = "CheckBoxButton",
        ) { isChecked ->
            Image(
                painter = painterResource(
                    if (isChecked) {
                        R.drawable.ic_checked
                    } else {
                        R.drawable.ic_unchecked
                    }
                ),
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    var checked by remember { mutableStateOf(false) }
    CheckBoxButton(
        checked = checked,
        onCheckedChange = { checked = it},
    )
}