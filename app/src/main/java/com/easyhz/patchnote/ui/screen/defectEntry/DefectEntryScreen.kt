package com.easyhz.patchnote.ui.screen.defectEntry

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.core.designSystem.component.bottomSheet.ImageBottomSheet
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.extension.noRippleClickable
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.ui.screen.defectEntry.component.DefectCategoryField
import com.easyhz.patchnote.ui.screen.defectEntry.component.DefectContentField
import com.easyhz.patchnote.ui.screen.defectEntry.component.DefectImageField
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntryIntent
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntrySideEffect
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefectEntryScreen(
    modifier: Modifier = Modifier,
    viewModel: DefectEntryViewModel = hiltViewModel(),
    navigateToUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
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
                    onClick = { }
                ),
                title = TopBarType.TopBarTitle(
                    stringId = R.string.defect_entry_title
                ),
                right = TopBarType.TopBarTextButton(
                    stringId = R.string.defect_entry_receipt,
                    textAlignment = Alignment.CenterEnd,
                    textColor = Primary,
                    onClick = { }
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
            uiState.entryItem.forEach { (category, value) ->
                DefectCategoryField(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    value = value,
                    onValueChange = {
                        viewModel.postIntent(
                            DefectEntryIntent.ChangeEntryValueTextValue(
                                categoryType = category,
                                value = it
                            )
                        )
                    },
                    category = category,
                    dropDownList = uiState.searchCategory.getOrDefault(category, emptyList()),
                    onClickDropDownList = {
                        viewModel.postIntent(
                            DefectEntryIntent.ClickCategoryDropDown(
                                categoryType = category,
                                value = it
                            )
                        )
                    },
                    onFocusChanged = {
                        viewModel.postIntent(
                            DefectEntryIntent.ChangeFocusState(
                                categoryType = category,
                                focusState = it
                            )
                        )
                    },
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
    }

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when(sideEffect) {
            is DefectEntrySideEffect.NavigateToGallery -> {
                galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            is DefectEntrySideEffect.NavigateToCamera -> {
                cameraLauncher.launch(sideEffect.uri)
            }
        }
    }
}