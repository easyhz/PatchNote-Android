package com.easyhz.patchnote.ui.screen.dataEntry

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.core.designSystem.component.category.CategoryEntryField
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.ui.screen.dataEntry.contract.DataEntryIntent
import com.easyhz.patchnote.ui.screen.dataEntry.contract.DataEntrySideEffect
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Primary

@Composable
fun DataEntryScreen(
    modifier: Modifier = Modifier,
    viewModel: DataEntryViewModel = hiltViewModel(),
    navigateToUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
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
                    stringId = R.string.data_entry_title
                ),
                right = TopBarType.TopBarTextButton(
                    stringId = R.string.data_entry_save,
                    textAlignment = Alignment.CenterEnd,
                    textColor = Primary,
                    onClick = { viewModel.postIntent(DataEntryIntent.NavigateToUp) }
                ),
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier.padding(innerPadding).fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(uiState.dataEntryList, key = { _, item -> item.id }) { index, dataEntryItem ->
                CategoryEntryField(
                    modifier = Modifier.animateItem(tween(300)),
                    selectedCategoryType = dataEntryItem.categoryType,
                    onSelected = { viewModel.postIntent(DataEntryIntent.SelectDataEntryItemCategoryType(index, it)) },
                    value = dataEntryItem.value,
                    onValueChange = { viewModel.postIntent(DataEntryIntent.ChangeDataEntryItemValue(index, it)) }
                ) { viewModel.postIntent(DataEntryIntent.DeleteDataEntryItem(index)) }
            }
        }
    }

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is DataEntrySideEffect.NavigateToUp -> navigateToUp()
        }
    }
}