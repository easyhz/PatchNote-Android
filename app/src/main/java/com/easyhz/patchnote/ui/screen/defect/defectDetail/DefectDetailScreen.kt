package com.easyhz.patchnote.ui.screen.defect.defectDetail

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.easyhz.patchnote.core.designSystem.component.bottomSheet.ListBottomSheet
import com.easyhz.patchnote.core.designSystem.component.button.MainButton
import com.easyhz.patchnote.core.designSystem.component.dialog.BasicDialog
import com.easyhz.patchnote.core.designSystem.component.header.DefectHeader
import com.easyhz.patchnote.core.designSystem.component.loading.LoadingIndicator
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.bottomSheet.DefectDetailBottomSheet
import com.easyhz.patchnote.core.designSystem.util.button.ButtonColor
import com.easyhz.patchnote.core.designSystem.util.dialog.BasicDialogButton
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.defect.DefectMainItem
import com.easyhz.patchnote.core.model.defect.DefectProgress
import com.easyhz.patchnote.core.model.defect.DefectUser
import com.easyhz.patchnote.ui.screen.defect.defectDetail.component.DetailField
import com.easyhz.patchnote.ui.screen.defect.defectDetail.contract.DetailIntent
import com.easyhz.patchnote.ui.screen.defect.defectDetail.contract.DetailSideEffect
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Primary
import com.easyhz.patchnote.ui.theme.Red
import com.easyhz.patchnote.ui.theme.SemiBold18
import com.easyhz.patchnote.ui.theme.SubBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefectDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DefectDetailViewModel = hiltViewModel(),
    navigateToUp: () -> Unit,
    navigateToDefectCompletion: (DefectMainItem) -> Unit,
    navigateToDefectEdit: (DefectItem) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                right = if (uiState.isOwner) TopBarType.TopBarIconButton(
                    iconId = R.drawable.ic_more_trailing,
                    iconAlignment = Alignment.CenterEnd,
                    tint = MainText,
                    onClick = { viewModel.postIntent(DetailIntent.ChangeStateBottomSheet(true)) }
                ) else null,
            )
        },
        bottomBar = {
            MainButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                text = if (uiState.defectItem?.progress == DefectProgress.DONE) stringResource(R.string.defect_completion) else stringResource(R.string.defect_do_done),
                buttonColor = ButtonColor(
                    disabledContentColor = MainText
                ),
                enabled = uiState.defectItem?.progress != DefectProgress.DONE,
            ) {
                viewModel.postIntent(DetailIntent.CompleteDefect)
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
                    requester = DefectUser.create(
                        uiState.defectItem!!.requesterName,
                        uiState.defectItem!!.requestDate
                    ),
                    worker = DefectUser.create(
                        uiState.defectItem!!.workerName,
                        uiState.defectItem!!.requestDate
                    )
                )
            }
            item {
                DetailField(
                    isComplete = uiState.defectItem!!.progress == DefectProgress.DONE,
                    tabs = uiState.defectItem!!.createDefectContent()
                )
            }
            item {
                Spacer(Modifier.height(40.dp))
            }
        }
        if (uiState.isShowBottomSheet) {
            ListBottomSheet(
                items = enumValues<DefectDetailBottomSheet>(),
                onDismissRequest = {
                    viewModel.postIntent(DetailIntent.ChangeStateBottomSheet(false))
                },
                onClick = {
                    viewModel.postIntent(DetailIntent.ClickBottomSheetItem(it))
                }
            )
        }

        uiState.dialogMessage?.let { error ->
            BasicDialog(
                title = error.title,
                content = error.message,
                positiveButton = BasicDialogButton(
                    text = stringResource(R.string.dialog_button),
                    style = SemiBold18.copy(color = MainBackground),
                    backgroundColor = Primary,
                    onClick = { viewModel.postIntent(DetailIntent.ShowError(null)) }
                ),
                negativeButton = null
            ) {
                viewModel.postIntent(DetailIntent.ShowError(null))
            }
        }

        if (uiState.isShowDeleteDialog) {
            BasicDialog(
                title = stringResource(R.string.defect_delete_dialog_title),
                content = null,
                positiveButton = BasicDialogButton(
                    text = stringResource(R.string.delete),
                    style = SemiBold18.copy(color = MainBackground),
                    backgroundColor = Red,
                    onClick = {
                        viewModel.postIntent(DetailIntent.DeleteDefect)
                    }
                ),
                negativeButton = BasicDialogButton(
                    text = stringResource(R.string.dialog_negative_button),
                    backgroundColor = SubBackground,
                    onClick = {
                        viewModel.postIntent(DetailIntent.ShowDeleteDialog(false))
                    }
                )
            ) {
                viewModel.postIntent(DetailIntent.ShowDeleteDialog(false))
            }
        }
    }

    LoadingIndicator(
        isLoading = uiState.isLoading,
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            DetailSideEffect.NavigateToUp -> {
                navigateToUp()
            }

            is DetailSideEffect.NavigateToDefectCompletion -> {
                navigateToDefectCompletion(sideEffect.defectMainItem)
            }

            is DetailSideEffect.NavigateToDefectEdit -> {
                navigateToDefectEdit(sideEffect.defectItem)
            }
        }
    }
}