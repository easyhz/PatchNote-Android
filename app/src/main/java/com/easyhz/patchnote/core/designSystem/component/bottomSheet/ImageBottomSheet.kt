package com.easyhz.patchnote.core.designSystem.component.bottomSheet

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.R
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Medium14

enum class ImageBottomSheetType(
    @StringRes val titleId: Int,
    @DrawableRes val iconId: Int
) {
    GALLERY(
        titleId = R.string.gallery,
        iconId = R.drawable.ic_album
    ),
    CAMERA(
        titleId = R.string.camera,
        iconId = R.drawable.ic_camera
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onDismissRequest: () -> Unit,
    onClick: (ImageBottomSheetType) -> Unit
) {
    BasicBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            ImageBottomSheetType.entries.forEach { item ->
                ImageBottomSheetItem(
                    type = item,
                    onClick = {
                        onClick(item)
                        onDismissRequest()
                    }
                )
            }
        }
    }
}

@Composable
private fun ImageBottomSheetItem(
    modifier: Modifier = Modifier,
    type: ImageBottomSheetType,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = type.iconId),
            contentDescription = null
        )
        Text(
            text = stringResource(id = type.titleId),
            style = Medium14,
            color = MainText
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ImageBottomSheetPreview() {
    ImageBottomSheet(
        onDismissRequest = {},
        onClick = {}
    )
}