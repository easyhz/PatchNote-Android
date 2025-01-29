package com.easyhz.patchnote.core.designSystem.component.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.designSystem.component.chip.FilterChip
import com.easyhz.patchnote.core.model.defect.DefectUser
import com.easyhz.patchnote.core.model.defect.DefectUserType
import com.easyhz.patchnote.ui.theme.Bold22
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Medium14
import com.easyhz.patchnote.ui.theme.Medium16
import com.easyhz.patchnote.ui.theme.Medium20
import com.easyhz.patchnote.ui.theme.SubBackground
import com.easyhz.patchnote.ui.theme.SubText

@Composable
fun DefectHeader(
    modifier: Modifier = Modifier,
    site: String,
    building: String,
    unit: String,
    part: String,
    space: String,
    workType: String,
    requester: DefectUser?,
    worker: DefectUser?
) {
    Column(
        modifier = modifier
            .padding(top = 12.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = site,
                style = Medium20,
                color = MainText
            )
            Text(
                text = stringResource(
                    R.string.home_defect_building_unit_format,
                    building,
                    unit
                ),
                style = Bold22,
                color = MainText
            )
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                listOf(part, space, workType)
            ) {
                FilterChip(
                    text = it,
                    style = Medium14.copy(color = MainText),
                    paddingValues = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            requester?.let {
                DefectUserRow(
                    user = it,
                    defectUserType = DefectUserType.REQUESTER
                )
            }
            worker?.let {
                DefectUserRow(
                    user = it,
                    defectUserType = DefectUserType.WORKER
                )
            }
        }

        Box(modifier = Modifier.height(8.dp).fillMaxWidth().background(SubBackground))
    }
}

@Composable
private fun DefectUserRow(
    modifier: Modifier = Modifier,
    user: DefectUser,
    defectUserType: DefectUserType,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = user.name + " " + stringResource(defectUserType.title),
            style = Medium16,
            color = MainText
        )
        Text(
            text = user.date,
            style = Medium16,
            color = SubText
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DefectHeaderPreview() {
    DefectHeader(
        site = "Site",
        building = "Building",
        unit = "Unit",
        part = "Part",
        space = "Space",
        workType = "WorkType",
        requester = DefectUser(
            name = "Requester",
            date = "Date"
        ),
        worker = DefectUser(
            name = "Worker",
            date = "Date"
        )
    )
}