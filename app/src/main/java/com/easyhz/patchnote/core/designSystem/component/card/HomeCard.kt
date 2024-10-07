package com.easyhz.patchnote.core.designSystem.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.designSystem.component.chip.FilterChip
import com.easyhz.patchnote.core.model.defect.DefectProgress
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.defect.DefectProgress.Companion.isDone
import com.easyhz.patchnote.ui.theme.Bold18
import com.easyhz.patchnote.ui.theme.DoneColor
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Medium14
import com.easyhz.patchnote.ui.theme.Regular14
import com.easyhz.patchnote.ui.theme.SemiBold16
import com.easyhz.patchnote.ui.theme.SubBackground
import com.easyhz.patchnote.ui.theme.SubText

@Composable
fun HomeCard(
    modifier: Modifier = Modifier,
    defectItem: DefectItem,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 20.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HomeCardImage(
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(8.dp)),
            imageUrl = defectItem.thumbnailUrl,
            progress = defectItem.progress
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = defectItem.site,
                    style = Medium14,
                    color = MainText
                )
                Text(
                    text = stringResource(
                        R.string.home_defect_building_unit_format,
                        defectItem.building,
                        defectItem.unit
                    ),
                    style = Bold18,
                    color = MainText
                )
                Text(
                    text = defectItem.requestDate,
                    style = Regular14,
                    color = SubText
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(defectItem.part, defectItem.space, defectItem.workType).forEach {
                    FilterChip(
                        modifier = Modifier.height(22.dp),
                        text = it,
                        style = Medium14.copy(color = MainText),
                        paddingValues = PaddingValues(horizontal = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeCardImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    progress: DefectProgress,
) {
    val backgroundColor = if (progress.isDone()) DoneColor else SubBackground

    Box(
        modifier = modifier.background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        ImageContent(imageUrl = imageUrl)
        DoneLabel(progress = progress)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ImageContent(
    modifier: Modifier = Modifier,
    imageUrl: String
) {
    if (imageUrl.isBlank()) return
    GlideImage(
        modifier = modifier,
        model = imageUrl,
        contentDescription = null,
        loading = placeholder(ColorPainter(SubBackground)),
        failure = placeholder(ColorPainter(SubBackground)),
        contentScale = ContentScale.Crop,
        transition = CrossFade
    )
}

@Composable
private fun DoneLabel(
    modifier: Modifier = Modifier,
    progress: DefectProgress
) {
    if (progress.isDone()) {
        Text(
            modifier = modifier,
            text = stringResource(R.string.defect_done),
            style = SemiBold16,
            color = MainBackground
        )
    }
}

@Preview
@Composable
private fun HomeCardPreview() {
    HomeCard(
        defectItem = DefectItem(
            id = "1",
            site = "site",
            building = "101",
            unit = "202",
            space = "space",
            part = "part",
            workType = "workType",
            progress = DefectProgress.REQUESTED,
            requestDate = "2021-10-01",
            beforeDescription = "description",
            afterDescription = "",
            afterImageUrls = emptyList(),
            beforeImageUrls = emptyList(),
            requesterName = "requester",
            requesterPhone = "010-1234-5678",
            workerId = "",
            workerName = "",
            workerPhone = "",
            completionDate = "",
            thumbnailUrl = "",
            beforeImageSizes = emptyList(),
            afterImageSizes = emptyList(),
            requesterId = "",
            search = emptyList()
        )
    )

}