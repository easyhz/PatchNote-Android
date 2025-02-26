package com.easyhz.patchnote.ui.screen.defectCompletion

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.core.designSystem.component.bottomSheet.ImageBottomSheet
import com.easyhz.patchnote.core.designSystem.component.dialog.BasicDialog
import com.easyhz.patchnote.core.designSystem.component.header.DefectHeader
import com.easyhz.patchnote.core.designSystem.component.loading.LoadingIndicator
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.dialog.BasicDialogButton
import com.easyhz.patchnote.core.designSystem.util.extension.noRippleClickable
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.defect.DefectUser
import com.easyhz.patchnote.ui.screen.defectCompletion.contract.DefectCompletionIntent
import com.easyhz.patchnote.ui.screen.defectCompletion.contract.DefectCompletionSideEffect
import com.easyhz.patchnote.ui.screen.defectEntry.component.DefectContentField
import com.easyhz.patchnote.ui.screen.defectEntry.component.DefectImageField
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Primary
import com.easyhz.patchnote.ui.theme.SemiBold18

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefectCompletionScreen(
    modifier: Modifier = Modifier,
    viewModel: DefectCompletionViewModel = hiltViewModel(),
    navigateToUp: () -> Unit,
    navigateToDefectDetail: (DefectItem) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(10),
            onResult = { viewModel.postIntent(DefectCompletionIntent.PickImages(it)) }
        )

    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
            onResult = { viewModel.postIntent(DefectCompletionIntent.TakePicture(it)) }
        )
    PatchNoteScaffold(
        topBar = {
            TopBar(
                left = TopBarType.TopBarIconButton(
                    iconId = R.drawable.ic_close_leading,
                    iconAlignment = Alignment.CenterStart,
                    tint = MainText,
                    onClick = {
                        viewModel.postIntent(DefectCompletionIntent.NavigateToUp)
                    }
                ),
                title = TopBarType.TopBarTitle(
                    stringId = R.string.defect_completion_title
                ),
                right = TopBarType.TopBarTextButton(
                    stringId = R.string.defect_completion_button,
                    textAlignment = Alignment.CenterEnd,
                    textColor = Primary,
                    onClick = { viewModel.postIntent(DefectCompletionIntent.ClickCompletion(id = uiState.defectMainItem.id)) }
                )
            )
        },
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .noRippleClickable {
                    focusManager.clearFocus()
                }
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DefectHeader(
                site = uiState.defectMainItem.site,
                building = uiState.defectMainItem.building,
                unit = uiState.defectMainItem.unit,
                part = uiState.defectMainItem.part,
                space = uiState.defectMainItem.space,
                workType = uiState.defectMainItem.workType,
                requester = DefectUser.create(uiState.defectMainItem.requesterName, uiState.defectMainItem.requestDate),
                worker = null
            )
            DefectContentField(
                modifier = Modifier.padding(horizontal = 20.dp),
                value = uiState.completionContent,
                onValueChange = {
                    viewModel.postIntent(DefectCompletionIntent.ChangeCompletionContent(it))
                },
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
            DefectImageField(
                images = uiState.images,
                onClickAdd = { viewModel.postIntent(DefectCompletionIntent.ChangeStateImageBottomSheet(true)) },
                onClickDelete = { viewModel.postIntent(DefectCompletionIntent.DeleteImage(it)) }
            )
            Spacer(modifier = Modifier.imePadding())
        }

        if (uiState.isShowImageBottomSheet) {
            ImageBottomSheet(
                onDismissRequest = {
                    viewModel.postIntent(DefectCompletionIntent.ChangeStateImageBottomSheet(false))
                },
                onClick = {
                    viewModel.postIntent(DefectCompletionIntent.ClickImageBottomSheet(it))
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
                    onClick = { viewModel.postIntent(DefectCompletionIntent.ShowError(null)) }
                ),
                negativeButton = null
            ) {
                viewModel.postIntent(DefectCompletionIntent.ShowError(null))
            }
        }
    }

    LoadingIndicator(
        isLoading = uiState.isLoading,
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when(sideEffect) {
            is DefectCompletionSideEffect.ClearFocus -> {
                focusManager.clearFocus()
            }
            is DefectCompletionSideEffect.NavigateToGallery -> {
                galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            is DefectCompletionSideEffect.NavigateToCamera -> {
                cameraLauncher.launch(sideEffect.uri)
            }
            is DefectCompletionSideEffect.NavigateToUp -> {
                navigateToUp()
            }
            is DefectCompletionSideEffect.NavigateToDefectDetail -> {
                navigateToDefectDetail(sideEffect.defectItem)
            }
        }
    }
}