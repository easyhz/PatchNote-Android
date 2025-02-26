package com.easyhz.patchnote.ui.screen.defect.defectEntry

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
import com.easyhz.patchnote.core.designSystem.component.dialog.DefectEntryDialog
import com.easyhz.patchnote.core.designSystem.component.loading.LoadingIndicator
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.dialog.BasicDialogButton
import com.easyhz.patchnote.core.designSystem.util.extension.noRippleClickable
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.ui.screen.defect.common.DefectViewModel
import com.easyhz.patchnote.ui.screen.defect.common.contract.DefectIntent
import com.easyhz.patchnote.ui.screen.defect.common.contract.DefectSideEffect
import com.easyhz.patchnote.ui.screen.defectEntry.component.DefectCategoryField
import com.easyhz.patchnote.ui.screen.defectEntry.component.DefectContentField
import com.easyhz.patchnote.ui.screen.defectEntry.component.DefectImageField
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntryIntent
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntrySideEffect
import com.easyhz.patchnote.ui.theme.LocalSnackBarHostState
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Primary
import com.easyhz.patchnote.ui.theme.SemiBold18

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefectEntryScreen(
    modifier: Modifier = Modifier,
    viewModel: DefectEntryViewModel = hiltViewModel(),
    defectViewModel: DefectViewModel = hiltViewModel(),
    navigateToUp: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val defectState by defectViewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val snackBarHost = LocalSnackBarHostState.current
    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(10),
            onResult = { viewModel.postIntent(DefectEntryIntent.PickImages(it)) }
        )

    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
            onResult = { viewModel.postIntent(DefectEntryIntent.TakePicture(it)) }
        )
    PatchNoteScaffold(
        topBar = {
            TopBar(
                left = TopBarType.TopBarIconButton(
                    iconId = R.drawable.ic_close_leading,
                    iconAlignment = Alignment.CenterStart,
                    tint = MainText,
                    onClick = { viewModel.postIntent(DefectEntryIntent.NavigateToUp) }
                ),
                title = TopBarType.TopBarTitle(
                    stringId = R.string.defect_entry_title
                ),
                right = TopBarType.TopBarTextButton(
                    stringId = R.string.defect_entry_receipt,
                    textAlignment = Alignment.CenterEnd,
                    textColor = Primary,
                    onClick = { defectViewModel.postIntent(DefectIntent.ValidateEntryItem) }
                ),
            )
        }
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
            defectState.entryItem.forEach { (category, value) ->
                DefectCategoryField(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    value = value,
                    onValueChange = {
                        defectViewModel.postIntent(
                            DefectIntent.ChangeEntryValueTextValue(
                                categoryType = category,
                                value = it
                            )
                        )
                    },
                    category = category,
                    dropDownList = defectState.searchCategory.getOrDefault(category, emptyList()),
                    onClickDropDownList = {
                        defectViewModel.postIntent(
                            DefectIntent.ClickCategoryDropDown(
                                categoryType = category,
                                value = it
                            )
                        )
                    },
                    onFocusChanged = {
                        defectViewModel.postIntent(
                            DefectIntent.ChangeFocusState(
                                categoryType = category,
                                focusState = it
                            )
                        )
                    },
                    onIconClick = { defectViewModel.postIntent(DefectIntent.ClearData(category)) },
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            }
            DefectContentField(
                modifier = Modifier.padding(horizontal = 20.dp),
                value = uiState.entryContent,
                onValueChange = {
                    viewModel.postIntent(DefectEntryIntent.ChangeEntryContent(it))
                },
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
            DefectImageField(
                images = uiState.images,
                onClickAdd = { viewModel.postIntent(DefectEntryIntent.ChangeStateImageBottomSheet(true)) },
                onClickDelete = { viewModel.postIntent(DefectEntryIntent.DeleteImage(it)) }
            )
            Spacer(modifier = Modifier.imePadding())
        }
        if (uiState.isShowImageBottomSheet) {
            ImageBottomSheet(
                onDismissRequest = {
                    viewModel.postIntent(DefectEntryIntent.ChangeStateImageBottomSheet(false))
                },
                onClick = {
                    viewModel.postIntent(DefectEntryIntent.ClickImageBottomSheet(it))
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
                    onClick = { viewModel.postIntent(DefectEntryIntent.ShowError(null)) }
                ),
                negativeButton = null
            ) {
                viewModel.postIntent(DefectEntryIntent.ShowError(null))
            }
        }

        if (uiState.isShowEntryDialog) {
            DefectEntryDialog(
                onClickSave = { viewModel.postIntent(DefectEntryIntent.SaveDefect) },
                onClickOfflineSave = { viewModel.postIntent(DefectEntryIntent.SaveOfflineDefect)  },
                onDismissRequest = { viewModel.postIntent(DefectEntryIntent.HideEntryDialog) }
            )
        }
    }


    LoadingIndicator(
        isLoading = uiState.isLoading,
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when(sideEffect) {
            is DefectEntrySideEffect.ClearFocus -> {
                focusManager.clearFocus()
            }
            is DefectEntrySideEffect.NavigateToGallery -> {
                galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            is DefectEntrySideEffect.NavigateToCamera -> {
                cameraLauncher.launch(sideEffect.uri)
            }
            is DefectEntrySideEffect.NavigateToUp -> {
                navigateToUp()
            }
            is DefectEntrySideEffect.NavigateToHome -> {
                navigateToHome()
            }
            is DefectEntrySideEffect.ShowSnackBar -> {
                snackBarHost.showSnackbar(
                    message = sideEffect.value,
                    withDismissAction = true
                )
            }
            is DefectEntrySideEffect.SendClear -> {
                defectViewModel.postIntent(DefectIntent.ClearAllData)
            }
        }
    }

    defectViewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when(sideEffect) {
            is DefectSideEffect.ValidateEntryItem -> {
                viewModel.postIntent(DefectEntryIntent.ClickReceipt(defectState.entryItem, sideEffect.invalidEntry))
            }
            is DefectSideEffect.SendError -> {
                viewModel.postIntent(DefectEntryIntent.ShowError(sideEffect.message))
            }
            is DefectSideEffect.SendLoading -> {
                viewModel.postIntent(DefectEntryIntent.SetLoading(sideEffect.isLoading))
            }
            else -> { }
        }
    }
}