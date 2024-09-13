package com.easyhz.patchnote.core.designSystem.component.topbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.designSystem.util.extension.noRippleClickable
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.core.designSystem.util.topbar.content
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.SemiBold18

@Composable
fun TopBar(
    left: TopBarType? = null,
    title: TopBarType? = null,
    right: TopBarType? = null,
) {
    Box(
        modifier = Modifier
            .height(48.dp)
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {
        left.content(
            modifier = Modifier.align(Alignment.CenterStart)
        )
        title.content(
            modifier = Modifier.align(Alignment.Center)
        )
        right.content(
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}


@Composable
fun TopBarTextButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    alignment: Alignment,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .noRippleClickable { onClick() },
        contentAlignment = alignment
    ) {
        Text(
            text = text,
            style = SemiBold18,
            color = textColor
        )
    }
}

@Composable
fun TopBarIconButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    alignment: Alignment,
    tint: Color,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .noRippleClickable { onClick() },
        contentAlignment = alignment
    ) {
        Icon(
            painter = painter,
            contentDescription = painter.toString(),
            tint = tint
        )
    }
}

@Preview
@Composable
private fun TopBarPreview() {
    TopBar(
        left = TopBarType.TopBarIconButton(
            iconId = R.drawable.ic_arrow_left_leading,
            iconAlignment = Alignment.CenterStart,
            tint = MainText,
            onClick = { }
        )
    )
}