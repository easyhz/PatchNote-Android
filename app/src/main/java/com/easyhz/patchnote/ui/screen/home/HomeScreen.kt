package com.easyhz.patchnote.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.core.designSystem.component.card.HomeCard
import com.easyhz.patchnote.core.designSystem.component.filter.HomeFilter
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.topbar.HomeTopBar
import com.easyhz.patchnote.core.model.home.DefectItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDataManagement: () -> Unit
) {
    PatchNoteScaffold(
        modifier = modifier,
        topBar = {
            Column {
                HomeTopBar {
                    navigateToDataManagement()
                }
                HomeFilter(
                    items = emptyList()
                ) {

                }
            }
        }
    ) { innerPadding ->
//        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            Text(
//                text = stringResource(R.string.home_defect_empty),
//                style = SemiBold16,
//                color = SubText
//            )
//        }
//        Box(modifier = Modifier
//            .padding(innerPadding)
//            .fillMaxSize()
//        ) {
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Key 설정
            items(10) {
                HomeCard(
                    modifier = Modifier.fillMaxWidth(),
                    defectItem = DefectItem(
                        id = "1",
                        site = "힐스테이트 양주옥정 파티오포레",
                        building = "101",
                        unit = "202",
                        space = "space",
                        part = "part",
                        workType = "workType",
                        isDone = false,
                        date = "2024.07.03",
                        thumbnailUrl = "thumbnailUrl"
                    )
                )
            }
        }
    }
}