package com.easyhz.patchnote.ui.screen.dataManagement

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.designSystem.component.category.categorySection
import com.easyhz.patchnote.core.designSystem.component.dialog.BasicDialog
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.dialog.BasicDialogButton
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.ui.screen.dataManagement.contract.DataIntent
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Red
import com.easyhz.patchnote.ui.theme.SemiBold18
import com.easyhz.patchnote.ui.theme.SubBackground

@Composable
fun DataManagementScreen(
    modifier: Modifier = Modifier,
    viewModel: DataManagementViewModel = hiltViewModel(),
    navigateToUp: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PatchNoteScaffold(
        topBar = {
            TopBar(
                left = TopBarType.TopBarIconButton(
                    iconId = R.drawable.ic_arrow_leading,
                    iconAlignment = Alignment.CenterStart,
                    tint = MainText,
                    onClick = { }
                ),
                title = TopBarType.TopBarTitle(
                    stringId = R.string.data_management_title
                ),
                right = TopBarType.TopBarIconButton(
                    iconId = R.drawable.ic_add_trailing,
                    iconAlignment = Alignment.CenterEnd,
                    tint = MainText,
                    onClick = { }
                ),
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier.padding(innerPadding).padding(top = 8.dp)
        ) {
            uiState.category.forEachIndexed { categoryIndex, category ->
                categorySection(category = category) { index ->
                    viewModel.postIntent(DataIntent.DeleteCategoryValue(categoryIndex, index))
                }
            }
        }

        if(uiState.isShowDeleteDialog) {
            BasicDialog(
                title = stringResource(R.string.data_management_delete_dialog_title, uiState.deleteItem),
                content = stringResource(R.string.data_management_delete_dialog_content),
                positiveButton = BasicDialogButton(
                    text = stringResource(R.string.data_management_delete_dialog_positive_button),
                    style = SemiBold18.copy(color = Color.White),
                    backgroundColor = Red,
                    onClick = { viewModel.postIntent(DataIntent.ClickPositiveButton) }
                ),
                negativeButton = BasicDialogButton(
                    text = stringResource(R.string.data_management_delete_dialog_negative_button),
                    backgroundColor = SubBackground,
                    onClick = { viewModel.postIntent(DataIntent.HideDeleteDialog) }
                )
            )
        }
    }
}

@Preview
@Composable
private fun DataManagementScreenPreview() {
    DataManagementScreen {  }
}