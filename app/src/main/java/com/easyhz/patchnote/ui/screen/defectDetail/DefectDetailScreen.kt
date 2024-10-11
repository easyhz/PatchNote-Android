package com.easyhz.patchnote.ui.screen.defectDetail

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.core.designSystem.component.button.MainButton
import com.easyhz.patchnote.core.designSystem.component.header.DefectHeader
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.core.model.defect.DefectUser
import com.easyhz.patchnote.ui.screen.defectDetail.component.DetailField
import com.easyhz.patchnote.ui.screen.defectDetail.contract.DetailIntent
import com.easyhz.patchnote.ui.screen.defectDetail.contract.DetailSideEffect
import com.easyhz.patchnote.ui.theme.MainText

@Composable
fun DefectDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DefectDetailViewModel = hiltViewModel(),
    defectId: String,
    navigateToUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.postIntent(DetailIntent.FetchData(defectId))
    }
    PatchNoteScaffold(
        topBar = {
            TopBar(
                left = TopBarType.TopBarIconButton(
                    iconId = R.drawable.ic_arrow_leading,
                    iconAlignment = Alignment.CenterStart,
                    tint = MainText,
                    onClick = {
                        viewModel.postIntent(DetailIntent.NavigateToUp)
                    }
                ),
                title = TopBarType.TopBarTitle(
                    stringId = R.string.defect_entry_title
                ),
                right = TopBarType.TopBarIconButton(
                    iconId = R.drawable.ic_more_trailing,
                    iconAlignment = Alignment.CenterEnd,
                    tint = MainText,
                    onClick = { }
                ),
            )
        },
        bottomBar = {
            MainButton(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp),
                text = stringResource(R.string.defect_do_done)
            ) {
                /* 완료 처리 */
            }
        }
    ) { innerPadding ->
        if (uiState.defectItem == null) return@PatchNoteScaffold
        LazyColumn(
            modifier = modifier.padding(top = innerPadding.calculateTopPadding(), bottom = 20.dp),
        ) {
            item {
                DefectHeader(
                    site = uiState.defectItem!!.site,
                    building = uiState.defectItem!!.building,
                    unit = uiState.defectItem!!.unit,
                    part = uiState.defectItem!!.part,
                    space = uiState.defectItem!!.space,
                    workType = uiState.defectItem!!.workType,
                    requester = DefectUser.create(uiState.defectItem!!.requesterName, uiState.defectItem!!.requestDate),
                    worker = DefectUser.create(uiState.defectItem!!.workerName, uiState.defectItem!!.requestDate)
                )
            }
            item {
                DetailField(
                    tabs = uiState.defectItem!!.createDefectContent()
                )
            }
            item {
                Spacer(Modifier.height(40.dp))
            }
        }
    }

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when(sideEffect) {
            DetailSideEffect.NavigateToUp -> {
                navigateToUp()
            }
        }
    }
}