package com.easyhz.patchnote.core.designSystem.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.core.designSystem.util.extension.noRippleClickable
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Medium14
import com.easyhz.patchnote.ui.theme.Medium18
import com.easyhz.patchnote.ui.theme.SubBackground
import com.easyhz.patchnote.ui.theme.SubText
import kotlin.random.Random

@Composable
fun MyPageCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    textColor: Color = MainText,
    enabledTitle: Boolean = true,
    iconContent: @Composable (() -> Unit)? = null,
    enabledClick: Boolean = false,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (enabledClick && iconContent == null) Modifier.clickable { onClick() }
                else Modifier
            )
            .padding(horizontal = 20.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (enabledTitle) {
            Text(
                text = title,
                style = Medium14,
                color = SubText
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(36.dp)
                    .weight(1f)
                    .then(
                        if (enabledClick && iconContent != null) Modifier.noRippleClickable { onClick() }
                        else Modifier
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = if (enabledTitle) value else title,
                    style = Medium18,
                    color = textColor
                )
                androidx.compose.animation.AnimatedVisibility(
                    visible = enabledTitle && value.isBlank(),
                ) {
                    Box(modifier = Modifier.fillMaxWidth(Random.nextFloat()).height(24.dp).background(SubBackground))
                }
            }
            iconContent?.invoke()
        }
    }

}


@Preview
@Composable
private fun MyPageCardPreview() {
    MyPageCard(
        title = "이름",
        value = "이름이",
    )
}