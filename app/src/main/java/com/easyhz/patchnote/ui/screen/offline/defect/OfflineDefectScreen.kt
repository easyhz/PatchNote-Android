package com.easyhz.patchnote.ui.screen.offline.defect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.core.designSystem.component.card.HomeCard
import com.easyhz.patchnote.core.designSystem.component.dialog.DialogButton
import com.easyhz.patchnote.core.designSystem.component.dialog.OnboardingDialog
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.topbar.HomeTopBar
import com.easyhz.patchnote.core.designSystem.util.dialog.BasicDialogButton
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarItem
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.ui.screen.offline.defect.contract.OfflineDefectIntent
import com.easyhz.patchnote.ui.screen.offline.defect.contract.OfflineDefectSideEffect
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.Primary
import com.easyhz.patchnote.ui.theme.SemiBold16
import com.easyhz.patchnote.ui.theme.SubBackground
import com.easyhz.patchnote.ui.theme.SubText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfflineDefectScreen(
    modifier: Modifier = Modifier,
    viewModel: OfflineDefectViewModel = hiltViewModel(),
    navigateToOfflineDefectDetail: (DefectItem) -> Unit,
    navigateToSetting: () -> Unit,
    navigateToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val defectList = viewModel.defectState.collectAsLazyPagingItems()
    val pullToRefreshState = rememberPullToRefreshState()

    PatchNoteScaffold(
        modifier = modifier,
        topBar = {
            HomeTopBar(
                title = stringResource(R.string.offline_defect_title),
                onClickName = { viewModel.postIntent(OfflineDefectIntent.ClickTopBarName) },
                topBarItem1 = TopBarItem(
                    painter = painterResource(R.drawable.ic_upload),
                    onClick = { viewModel.postIntent(OfflineDefectIntent.ClickUpload) }
                ),
                topBarItem2 = TopBarItem(
                    painter = painterResource(R.drawable.ic_setting),
                    onClick = { viewModel.postIntent(OfflineDefectIntent.ClickSetting) }
                )

            )
        },
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            isRefreshing = uiState.isRefreshing,
            onRefresh = { viewModel.postIntent(OfflineDefectIntent.Refresh) },
            state = pullToRefreshState,
            indicator = {
                Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    state = pullToRefreshState,
                    isRefreshing = uiState.isRefreshing,
                    containerColor = SubBackground,
                    color = Primary
                )
            }
        ) {
            if (defectList.itemCount == 0 && defectList.loadState.refresh is LoadState.NotLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(R.string.home_defect_empty),
                        style = SemiBold16,
                        color = SubText
                    )
                }
            }
            if (defectList.loadState.refresh is LoadState.Error) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.home_defect_error),
                            style = SemiBold16,
                            color = SubText
                        )
                        DialogButton(
                            modifier = Modifier.width(120.dp),
                            dialogButton = BasicDialogButton(
                                text = stringResource(R.string.home_defect_error_button),
                                backgroundColor = Primary,
                                style = SemiBold16.copy(color = MainBackground),
                                onClick = { viewModel.postIntent(OfflineDefectIntent.Refresh) }
                            )
                        )
                    }
                }
            }
            LazyColumn(
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(defectList.itemCount) { index ->
                    defectList[index]?.let { defectItem ->
                        HomeCard(
                            modifier = Modifier.fillMaxWidth(),
                            defectItem = defectItem
                        ) { viewModel.postIntent(OfflineDefectIntent.NavigateToOfflineDefectDetail(defectItem)) }
                    }
                }
            }
        }

        if (uiState.isShowOnboardingDialog) {
            OnboardingDialog(
                onDismissRequest = {
                    viewModel.postIntent(OfflineDefectIntent.HideOnboardingDialog)
                }
            )
        }
    }

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when(sideEffect) {
            is OfflineDefectSideEffect.NavigateToSetting -> {
                navigateToSetting()
            }
            is OfflineDefectSideEffect.NavigateToLogin -> {
                navigateToLogin()
            }
            is OfflineDefectSideEffect.NavigateToOfflineDefectDetail -> {
                navigateToOfflineDefectDetail(sideEffect.defectItem)
            }
        }
    }
}