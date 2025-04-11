package com.easyhz.patchnote.ui.screen.image.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.core.designSystem.util.extension.BorderType
import com.easyhz.patchnote.core.designSystem.util.extension.border
import com.easyhz.patchnote.core.model.image.DisplayImage
import com.easyhz.patchnote.core.model.image.DisplayImageType
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.SemiBold14

@Composable
fun ImageDisplayInformation(
    modifier: Modifier = Modifier,
    displayImage: DisplayImage,
) {
    val maxWidth = remember { 200.dp }
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(
                color = MainBackground.copy(0.7f)
            )
            .padding(4.dp),
    ) {
        displayImage.toMap()
            .filterValues { it != null }
            .forEach { (type, value) ->
                InformationRow(
                    modifier = Modifier
                        .widthIn(max = maxWidth)
                        .padding(horizontal = 4.dp)
                    ,
                    title = stringResource(type.displayNameId),
                    value = value.orEmpty()
                )
                if (type != DisplayImageType.entries.last()) {
                    HorizontalDivider(
                        modifier = Modifier.widthIn(max = maxWidth),
                        color = MainText,
                        thickness = 1.dp
                    )
                }
            }

    }
}

@Composable
private fun InformationRow(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .width(width = 44.dp)
                .padding(vertical = 2.dp),
            text = title,
            style = SemiBold14,
        )
        Text(
            modifier = Modifier
                .border(
                    type = BorderType.LEFT,
                    color = MainText,
                    width = 1.dp
                )
                .padding(horizontal = 4.dp)
                .padding(vertical = 2.dp),
            text = value,
            style = SemiBold14,
        )
    }
}


@Preview
@Composable
private fun ImageDisplayInformationPreview() {
    ImageDisplayInformation(
        displayImage = DisplayImage(
            site = "site",
            buildingUnit = "buildingUnitbuildingUnit",
            space = "space",
            part = "part",
            workType = "workType",
            request = "2023.01.22 김민수",
            completion = "completion"
        )
    )
}