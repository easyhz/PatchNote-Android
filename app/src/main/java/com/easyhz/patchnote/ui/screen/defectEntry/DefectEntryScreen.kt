package com.easyhz.patchnote.ui.screen.defectEntry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.extension.noRippleClickable
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.ui.screen.defectEntry.component.DefectCategoryField
import com.easyhz.patchnote.ui.screen.defectEntry.contract.DefectEntryIntent
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Primary

@Composable
fun DefectEntryScreen(
    modifier: Modifier = Modifier,
    viewModel: DefectEntryViewModel = hiltViewModel(),
    navigateToUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
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
                .imePadding()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            uiState.entryItem.forEach { (category, value) ->
                DefectCategoryField(
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
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            }
        }
    }
}