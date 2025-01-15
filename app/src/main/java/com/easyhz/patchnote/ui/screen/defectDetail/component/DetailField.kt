package com.easyhz.patchnote.ui.screen.defectDetail.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.model.defect.DefectContent
import com.easyhz.patchnote.core.model.defect.DefectProgress
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Medium18
import com.easyhz.patchnote.ui.theme.SemiBold18
import com.easyhz.patchnote.ui.theme.SubText

@Composable
fun DetailField(
    modifier: Modifier = Modifier,
    isComplete: Boolean,
    tabs: List<DefectContent>
) {
    val pagerState = rememberPagerState {
        tabs.size
    }
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    LaunchedEffect(selectedIndex) {
        pagerState.animateScrollToPage(selectedIndex)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedIndex = pagerState.currentPage
        }
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = selectedIndex,
            containerColor = MainBackground,
            contentColor = MainText,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedIndex]),
                    color = MainText
                )
            }
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedIndex == index,
                    interactionSource = null,
                    selectedContentColor = MainText,
                    unselectedContentColor = SubText,
                    onClick = { selectedIndex = index },
                ) {
                    Box(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = stringResource(id = tab.progress.stringId),
                            style = SemiBold18,
                        )
                    }
                }
            }
        }

        AnimatedContent(
            targetState = selectedIndex,
            transitionSpec = {
                if (selectedIndex == 0) {
                    (slideInHorizontally { width -> -width } + fadeIn()).togetherWith(
                        slideOutHorizontally { width -> width } + fadeOut())
                } else {
                    (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                        slideOutHorizontally { width -> -width } + fadeOut())
                }.using(SizeTransform(clip = false))
            },
            label = "DetailField",
        ) { page ->
            AnimatedContent(
                targetState = isComplete,
                transitionSpec = {
                    fadeIn() togetherWith  fadeOut()
                },
                label = "",
            ) { isComplete ->
                if (isComplete && page == 1 && tabs[page].description.isBlank() && tabs[page].imageUrls.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .height(500.dp)
                            .fillMaxWidth(), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.defect_completion_description),
                            style = Medium18,
                            color = MainText,
                        )
                    }
                } else if (page == 1) {
                    Box(
                        modifier = Modifier
                            .height(500.dp)
                            .fillMaxWidth(), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.defect_not_done),
                            style = Medium18,
                            color = MainText,
                        )
                    }
                }
            }
            Column(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = tabs[page].description,
                    style = Medium18,
                    color = MainText,
                    textAlign = TextAlign.Start
                )
                tabs[page].imageUrls.forEachIndexed { index, url ->
                    Column(
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        DetailImage(
                            url = url,
                            imageSize = tabs[page].imageSizes[index],
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun DetailFieldPreview() {
    DetailField(
        isComplete = true,
        tabs = listOf(
            DefectContent(
                DefectProgress.REQUESTED,
                "Requested",
                emptyList(),
                emptyList()
            ),
            DefectContent(
                DefectProgress.DONE,
                "done",
                emptyList(),
                emptyList()
            ),
        )
    )
}