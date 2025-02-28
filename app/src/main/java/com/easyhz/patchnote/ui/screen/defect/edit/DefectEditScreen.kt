package com.easyhz.patchnote.ui.screen.defect.edit

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
import com.easyhz.patchnote.core.designSystem.component.loading.LoadingIndicator
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.dialog.BasicDialogButton
import com.easyhz.patchnote.core.designSystem.util.extension.noRippleClickable
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.ui.screen.defect.common.DefectViewModel
import com.easyhz.patchnote.ui.screen.defect.common.contract.DefectIntent
import com.easyhz.patchnote.ui.screen.defect.common.contract.DefectSideEffect
import com.easyhz.patchnote.ui.screen.defect.defectEntry.component.DefectCategoryField
import com.easyhz.patchnote.ui.screen.defect.defectEntry.component.DefectContentField
import com.easyhz.patchnote.ui.screen.defect.defectEntry.component.DefectImageField
import com.easyhz.patchnote.ui.screen.defect.edit.contract.DefectEditIntent
import com.easyhz.patchnote.ui.screen.defect.edit.contract.DefectEditSideEffect
import com.easyhz.patchnote.ui.theme.LocalSnackBarHostState
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Primary
import com.easyhz.patchnote.ui.theme.SemiBold18

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefectEditScreen(
    modifier: Modifier = Modifier,
    viewModel: DefectEditViewModel = hiltViewModel(),
    defectViewModel: DefectViewModel = hiltViewModel(),
    navigateToUp: () -> Unit,
    navigateToDefectDetail: (DefectItem) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val defectState by defectViewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val snackBarHost = LocalSnackBarHostState.current
    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(10),
            onResult = { viewModel.postIntent(DefectEditIntent.PickImages(it)) }
        )

    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
            onResult = { viewModel.postIntent(DefectEditIntent.TakePicture(it)) }
        )
    PatchNoteScaffold(
        topBar = {
            TopBar(
                left = TopBarType.TopBarIconButton(
                    iconId = R.drawable.ic_close_leading,
                    iconAlignment = Alignment.CenterStart,
                    tint = MainText,
                    onClick = { viewModel.postIntent(DefectEditIntent.NavigateToUp) }
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
                    viewModel.postIntent(DefectEditIntent.ChangeEntryContent(it))
                },
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
            DefectImageField(
                images = uiState.images,
                onClickAdd = { viewModel.postIntent(DefectEditIntent.ChangeStateImageBottomSheet(true)) },
                onClickDelete = { viewModel.postIntent(DefectEditIntent.DeleteImage(it)) }
            )
            Spacer(modifier = Modifier.imePadding())
        }
        if (uiState.isShowImageBottomSheet) {
            ImageBottomSheet(
                onDismissRequest = {
                    viewModel.postIntent(DefectEditIntent.ChangeStateImageBottomSheet(false))
                },
                onClick = {
                    viewModel.postIntent(DefectEditIntent.ClickImageBottomSheet(it))
                }
            )
        }

        uiState.dialogMessage?.let { dialog ->
            BasicDialog(
                title = dialog.title,
                content = dialog.message,
                positiveButton = BasicDialogButton(
                    text = stringResource(R.string.dialog_button),
                    style = SemiBold18.copy(color = MainBackground),
                    backgroundColor = Primary,
                    onClick = { viewModel.postIntent(DefectEditIntent.SetDialog(null)) }
                ),
                negativeButton = null
            ) {
                viewModel.postIntent(DefectEditIntent.SetDialog(null))
            }
        }

        if (uiState.isShowEntryDialog) {
//            DefectEntryDialog(
//                onClickSave = { viewModel.postIntent(DefectUpdateIntent.SaveDefect) },
//                onClickOfflineSave = { viewModel.postIntent(DefectUpdateIntent.SaveOfflineDefect)  },
//                onDismissRequest = { viewModel.postIntent(DefectUpdateIntent.HideEntryDialog) }
//            )
        }
    }


    LoadingIndicator(
        isLoading = uiState.isLoading,
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when(sideEffect) {
            is DefectEditSideEffect.ClearFocus -> {
                focusManager.clearFocus()
            }
            is DefectEditSideEffect.NavigateToGallery -> {
                galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            is DefectEditSideEffect.NavigateToCamera -> {
                cameraLauncher.launch(sideEffect.uri)
            }
            is DefectEditSideEffect.NavigateToUp -> {
                navigateToUp()
            }
            is DefectEditSideEffect.NavigateToDefectDetail -> {
                navigateToDefectDetail(sideEffect.defectItem)
            }
            is DefectEditSideEffect.ShowSnackBar -> {
                snackBarHost.showSnackbar(
                    message = sideEffect.value,
                    withDismissAction = true
                )
            }
            is DefectEditSideEffect.SendEntryItem -> {
                println("SendEntryItem <- DefectEditScreen ${sideEffect.entryItem}")
                defectViewModel.postIntent(DefectIntent.SendEntryItem(sideEffect.entryItem))
            }
//            is DefectUpdateSideEffect.SendClear -> {
//                defectViewModel.postIntent(DefectIntent.ClearAllData)
//            }
        }
    }

    defectViewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when(sideEffect) {
            is DefectSideEffect.ValidateEntryItem -> {
                viewModel.postIntent(DefectEditIntent.ClickReceipt(defectState.entryItem, sideEffect.invalidEntry))
            }
            is DefectSideEffect.SendError -> {
                viewModel.postIntent(DefectEditIntent.SetDialog(sideEffect.message))
            }
            is DefectSideEffect.SendLoading -> {
                viewModel.postIntent(DefectEditIntent.SetLoading(sideEffect.isLoading))
            }
            else -> { }
        }
    }
}