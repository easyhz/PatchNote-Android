package com.easyhz.patchnote.ui.screen.defect.offline.detail

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
import com.easyhz.patchnote.core.common.util.toDateTimeString
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
import com.easyhz.patchnote.core.model.defect.DefectProgress
import com.easyhz.patchnote.core.model.defect.DefectUser
import com.easyhz.patchnote.ui.screen.defect.defectDetail.component.DetailField
import com.easyhz.patchnote.ui.screen.defect.offline.detail.contract.OfflineDefectDetailIntent
import com.easyhz.patchnote.ui.screen.defect.offline.detail.contract.OfflineDefectDetailSideEffect
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Primary
import com.easyhz.patchnote.ui.theme.Red
import com.easyhz.patchnote.ui.theme.SemiBold18
import com.easyhz.patchnote.ui.theme.SubBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfflineDefectDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: OfflineDefectDetailViewModel = hiltViewModel(),
    navigateToUp: () -> Unit,
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
                        viewModel.postIntent(OfflineDefectDetailIntent.NavigateToUp)
                    }
                ),
                title = TopBarType.TopBarTitle(
                    stringId = R.string.offline_defect_detail_title
                ),
                right = TopBarType.TopBarIconButton(
                    iconId = R.drawable.ic_more_trailing,
                    iconAlignment = Alignment.CenterEnd,
                    tint = MainText,
                    onClick = { viewModel.postIntent(OfflineDefectDetailIntent.ChangeStateBottomSheet(true)) }
                ),
            )
        },
        bottomBar = {
            MainButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                text = stringResource(R.string.offline_defect_detail_button),
                buttonColor = ButtonColor(
                    disabledContentColor = MainText
                ),
                enabled = uiState.defectItem?.progress != DefectProgress.DONE,
            ) {
                viewModel.postIntent(OfflineDefectDetailIntent.ClickMainButton)
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
                    requester = null,
                    worker = null,
                    offline = DefectUser(
                        name = uiState.defectItem!!.requesterName,
                        date = uiState.defectItem!!.requestDate.toDateTimeString(),
                    )
                )
            }

            item {
                DetailField(
                    isComplete = uiState.defectItem!!.progress == DefectProgress.DONE,
                    tabs = uiState.defectItem!!.createDefectContent(),
                    onClickImage = { _, _ -> /* TODO:  */ },
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
                    viewModel.postIntent(OfflineDefectDetailIntent.ChangeStateBottomSheet(false))
                },
                onClick = {
                    viewModel.postIntent(OfflineDefectDetailIntent.ClickBottomSheetItem(it))
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
                    onClick = { viewModel.postIntent(OfflineDefectDetailIntent.ShowError(null)) }
                ),
                negativeButton = null
            ) {
                viewModel.postIntent(OfflineDefectDetailIntent.ShowError(null))
            }
        }

        if (uiState.isShowUploadDialog) {
            BasicDialog(
                title = stringResource(R.string.offline_defect_upload_dialog_title),
                content = stringResource(R.string.offline_defect_detail_upload_dialog_content),
                positiveButton = BasicDialogButton(
                    text = stringResource(R.string.offline_defect_detail_upload_dialog_positive),
                    style = SemiBold18.copy(color = MainBackground),
                    backgroundColor = Primary,
                    onClick = {
                        viewModel.postIntent(OfflineDefectDetailIntent.UploadOfflineDefect)
                    }
                ),
                negativeButton = BasicDialogButton(
                    text = stringResource(R.string.offline_defect_detail_upload_dialog_negative),
                    backgroundColor = SubBackground,
                    onClick = {
                        viewModel.postIntent(OfflineDefectDetailIntent.ShowDeleteDialog(false))
                    }
                )
            ) {
                viewModel.postIntent(OfflineDefectDetailIntent.ShowDeleteDialog(false))
            }
        }

        if (uiState.isShowDeleteDialog) {
            BasicDialog(
                title = stringResource(R.string.offline_defect_detail_delete_dialog_title),
                content = stringResource(R.string.offline_defect_detail_delete_dialog_content),
                positiveButton = BasicDialogButton(
                    text = stringResource(R.string.offline_defect_detail_delete_dialog_positive),
                    style = SemiBold18.copy(color = MainBackground),
                    backgroundColor = Red,
                    onClick = {
                        viewModel.postIntent(OfflineDefectDetailIntent.DeleteDefect)
                    }
                ),
                negativeButton = BasicDialogButton(
                    text = stringResource(R.string.offline_defect_detail_delete_dialog_negative),
                    backgroundColor = SubBackground,
                    onClick = {
                        viewModel.postIntent(OfflineDefectDetailIntent.ShowDeleteDialog(false))
                    }
                )
            ) {
                viewModel.postIntent(OfflineDefectDetailIntent.ShowDeleteDialog(false))
            }
        }

        if (uiState.isShowUploadSuccessDialog) {
            BasicDialog(
                title = stringResource(R.string.offline_defect_success_dialog_title),
                content = stringResource(R.string.offline_defect_success_dialog_content),
                positiveButton = BasicDialogButton(
                    text = stringResource(R.string.offline_defect_success_dialog_button),
                    style = SemiBold18.copy(color = MainBackground),
                    backgroundColor = Primary,
                    onClick = { viewModel.postIntent(OfflineDefectDetailIntent.HideUploadSuccessDialog) }
                ),
                negativeButton = null
            )
        }
    }

    LoadingIndicator(
        isLoading = uiState.isLoading,
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is OfflineDefectDetailSideEffect.NavigateToUp -> {
                navigateToUp()
            }
            is OfflineDefectDetailSideEffect.NavigateToDefectEdit -> {
                navigateToDefectEdit(sideEffect.defectItem)
            }
        }
    }
}