package com.easyhz.patchnote.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.core.designSystem.component.button.HomeFloatingActionButton
import com.easyhz.patchnote.core.designSystem.component.card.HomeCard
import com.easyhz.patchnote.core.designSystem.component.filter.HomeFilter
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.topbar.HomeTopBar
import com.easyhz.patchnote.ui.screen.home.contract.HomeSideEffect
import com.easyhz.patchnote.ui.theme.SemiBold16
import com.easyhz.patchnote.ui.theme.SubText

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDataManagement: () -> Unit,
    navigateToDefectEntry: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
        },
        floatingActionButton = {
            HomeFloatingActionButton { navigateToDefectEntry() }
        }
    ) { innerPadding ->
        if (uiState.defectList.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(R.string.home_defect_empty),
                    style = SemiBold16,
                    color = SubText
                )
            }
        }
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(uiState.defectList, key = { it.id }) { defectItem ->
                HomeCard(
                    modifier = Modifier.fillMaxWidth(),
                    defectItem = defectItem
                )
            }
        }
    }

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when(sideEffect) {
            is HomeSideEffect.NavigateToDataManagement -> {
                navigateToDataManagement()
            }
            is HomeSideEffect.NavigateToDefectEntry -> {
                navigateToDefectEntry()
            }
        }
    }
}