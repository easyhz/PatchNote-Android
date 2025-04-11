package com.easyhz.patchnote.ui.screen.image.detail.component

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.R
import com.easyhz.patchnote.ui.theme.SemiBold18
import com.easyhz.patchnote.ui.theme.SubBackground

enum class ImageDetailBottomBarType(
    @StringRes val title: Int,
) {
    SAVE_ALL(
        title = R.string.image_detail_save_all,
    ),
    SAVE_ONE(
        title = R.string.image_detail_save_one,
    ),
}

@Composable
fun ImageDetailBottomBar(
    modifier: Modifier = Modifier,
    onClick: (ImageDetailBottomBarType) -> Unit,
) {
    val haptic = LocalHapticFeedback.current

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ImageDetailBottomBarType.entries.forEach { type ->
            ImageSaveButton(
                title = stringResource(type.title),
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onClick(type)
                }
            )
        }
    }
}

@Composable
private fun ImageSaveButton(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .heightIn(min = 40.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(SubBackground)
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_image),
            contentDescription = null,
        )
        Text(
            text = title,
            style = SemiBold18
        )
    }
}


@Preview
@Composable
private fun ImageDetailBottomBarPreview() {
    ImageDetailBottomBar { }
}

@Preview
@Composable
private fun ImageSaveButtonPreview() {
    ImageSaveButton(
        title = "Save",
        onClick = { },
    )
}