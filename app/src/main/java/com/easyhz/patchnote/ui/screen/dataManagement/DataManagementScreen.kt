package com.easyhz.patchnote.ui.screen.dataManagement

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.designSystem.component.category.categorySection
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.ui.theme.MainText

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
            modifier = modifier.padding(innerPadding)
        ) {
            uiState.category.forEach { category ->
                categorySection(category = category)
            }
        }
    }
}

@Preview
@Composable
private fun DataManagementScreenPreview() {
    DataManagementScreen {  }
}