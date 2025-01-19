package com.easyhz.patchnote.core.designSystem.component.toggle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Medium18
import com.easyhz.patchnote.ui.theme.PlaceholderText
import com.easyhz.patchnote.ui.theme.Primary

@Composable
fun SettingToggle(
    modifier: Modifier = Modifier,
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth().height(56.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = Medium18,
            color = MainText
        )

        Box(
            modifier = Modifier
                .width(48.dp)
                .height(32.dp)
        ) {
            Switch(
                modifier = Modifier
                    .scale(0.8f)
                    .align(Alignment.CenterEnd),
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MainBackground,
                    checkedTrackColor = Primary,
                    checkedBorderColor = Color.Transparent,
                    uncheckedThumbColor = MainBackground,
                    uncheckedTrackColor = PlaceholderText,
                    uncheckedBorderColor = Color.Transparent
                )
            )
        }
    }
}

@Preview
@Composable
private fun SettingTogglePreview() {
    var isChecked by remember { mutableStateOf(true) }
    SettingToggle(
        modifier = Modifier.padding(horizontal = 20.dp),
        title = "Title",
        isChecked = isChecked,
        onCheckedChange = {
            isChecked = it
        }
    )

}