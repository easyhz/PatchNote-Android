package com.easyhz.patchnote.ui.screen.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.core.designSystem.component.button.HomeFloatingActionButton
import com.easyhz.patchnote.core.designSystem.component.card.HomeCard
import com.easyhz.patchnote.core.designSystem.component.dialog.BasicDialog
import com.easyhz.patchnote.core.designSystem.component.dialog.InputDialog
import com.easyhz.patchnote.core.designSystem.component.filter.HomeFilter
import com.easyhz.patchnote.core.designSystem.component.loading.LoadingIndicator
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.textField.BaseTextField
import com.easyhz.patchnote.core.designSystem.component.topbar.HomeTopBar
import com.easyhz.patchnote.core.designSystem.util.dialog.BasicDialogButton
import com.easyhz.patchnote.core.model.filter.FilterParam
import com.easyhz.patchnote.ui.screen.home.contract.HomeIntent
import com.easyhz.patchnote.ui.screen.home.contract.HomeSideEffect
import com.easyhz.patchnote.ui.theme.Primary
import com.easyhz.patchnote.ui.theme.SemiBold16
import com.easyhz.patchnote.ui.theme.SemiBold18
import com.easyhz.patchnote.ui.theme.SubBackground
import com.easyhz.patchnote.ui.theme.SubText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    filterParam: FilterParam,
    navigateToSetting: (String) -> Unit,
    navigateToDefectEntry: () -> Unit,
    navigateToFilter: (FilterParam) -> Unit,
    navigateToDefectDetail: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val pullToRefreshState = rememberPullToRefreshState()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(filterParam) {
        viewModel.postIntent(HomeIntent.FetchData(filterParam))
    }
    PatchNoteScaffold(
        modifier = modifier,
        topBar = {
            HomeTopBar(
                onClickName = { viewModel.postIntent(HomeIntent.NavigateToNotion) },
                onClickSetting = { viewModel.postIntent(HomeIntent.ClickSetting) },
                onClickExport = { viewModel.postIntent(HomeIntent.ClickExport) },
            )
        },
        floatingActionButton = {
            HomeFloatingActionButton { navigateToDefectEntry() }
        }
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            isRefreshing = uiState.isRefreshing,
            onRefresh = { viewModel.postIntent(HomeIntent.Refresh(filterParam)) },
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
            if (uiState.defectList.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(R.string.home_defect_empty),
                        style = SemiBold16,
                        color = SubText
                    )
                }
            }
            Column {
                HomeFilter(
                    items = filterParam.toList(context),
                ) {
                    viewModel.postIntent(HomeIntent.NavigateToFilter)
                }
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(uiState.defectList, key = { it.id }) { defectItem ->
                        HomeCard(
                            modifier = Modifier.fillMaxWidth(),
                            defectItem = defectItem
                        ) { viewModel.postIntent(HomeIntent.NavigateToDefectDetail(defectItem.id)) }
                    }
                }
            }
        }
        if (!uiState.isLatestVersion) {
            BasicDialog(
                title = stringResource(R.string.version_dialog_title),
                content = null,
                positiveButton = BasicDialogButton(
                    text = stringResource(R.string.version_dialog_button),
                    style = SemiBold18.copy(color = Color.White),
                    backgroundColor = Primary,
                    onClick = { viewModel.postIntent(HomeIntent.UpdateAppVersion) }
                ),
                negativeButton = null
            )
        }

        if (uiState.isShowPasswordDialog) {
            InputDialog(
                title = stringResource(R.string.dialog_setting_password_title),
                subTitle = stringResource(R.string.dialog_setting_password_subtitle),
                content = {
                    BaseTextField(
                        modifier = Modifier.focusRequester(focusRequester),
                        containerModifier = Modifier.height(40.dp),
                        value = uiState.password,
                        onValueChange = { viewModel.postIntent(HomeIntent.ChangePasswordText(it)) },
                        title = null,
                        placeholder = stringResource(R.string.dialog_setting_password_content_placeholder),
                        singleLine = true,
                        isFilled = false,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        keyboardActions = KeyboardActions(onDone = { viewModel.postIntent(HomeIntent.CheckPassword) })
                    )
                },
                positiveButton = BasicDialogButton(
                    text = stringResource(R.string.dialog_setting_password_positive),
                    style = SemiBold18.copy(color = Color.White),
                    backgroundColor = Primary,
                    onClick = { viewModel.postIntent(HomeIntent.CheckPassword) }
                ),
                negativeButton = BasicDialogButton(
                    text = stringResource(R.string.dialog_setting_password_negative),
                    backgroundColor = SubBackground,
                    onClick = { viewModel.postIntent(HomeIntent.HidePasswordDialog) }
                )
            )
        }
        
        if(uiState.isShowPasswordErrorDialog) {
            InputDialog(
                title = stringResource(R.string.dialog_setting_password_error_title),
                subTitle = stringResource(R.string.dialog_setting_password_error_subtitle),
                content = null,
                positiveButton = BasicDialogButton(
                    text = stringResource(R.string.dialog_setting_password_error_button),
                    style = SemiBold18.copy(color = Color.White),
                    backgroundColor = Primary,
                    onClick = { viewModel.postIntent(HomeIntent.HidePasswordErrorDialog) }
                ),
                negativeButton = null
            )
        }

        if(uiState.isShowExportDialog) {
            BasicDialog(
                title = stringResource(R.string.dialog_export_title),
                content = stringResource(R.string.dialog_export_content),
                positiveButton = BasicDialogButton(
                    text = stringResource(R.string.dialog_export_positive),
                    style = SemiBold18.copy(color = Color.White),
                    backgroundColor = Primary,
                    onClick = { viewModel.postIntent(HomeIntent.ExportData) }
                ),
                negativeButton = BasicDialogButton(
                    text = stringResource(R.string.dialog_negative_button),
                    backgroundColor = SubBackground,
                    onClick = { viewModel.postIntent(HomeIntent.HideExportDialog) }
                )
            )
        }
    }

    LoadingIndicator(
        isLoading = uiState.isLoading
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            viewModel.postIntent(HomeIntent.ShowExportDialog)
        }
    }
    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when(sideEffect) {
            is HomeSideEffect.NavigateToSetting -> {
                navigateToSetting(sideEffect.url)
            }
            is HomeSideEffect.NavigateToDefectEntry -> {
                navigateToDefectEntry()
            }
            is HomeSideEffect.NavigateToFilter -> {
                navigateToFilter(filterParam)
            }
            is HomeSideEffect.NavigateToDefectDetail -> {
                navigateToDefectDetail(sideEffect.defectId)
            }
            is HomeSideEffect.NavigateToVersionUpdate -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(sideEffect.url))
                context.startActivity(intent)
            }
            is HomeSideEffect.RequestFocus -> {
                focusRequester.requestFocus()
            }
            is HomeSideEffect.RequestPermission -> {
                val hasPermission = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED

                if (hasPermission) {
                    viewModel.postIntent(HomeIntent.ShowExportDialog)
                } else {
                    permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }
            is HomeSideEffect.ShareIntent -> {
                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.file_provider",
                    sideEffect.file
                )

                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "application/vnd.ms-excel"
                    putExtra(Intent.EXTRA_STREAM, uri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                viewModel.postIntent(HomeIntent.SetLoading(false))
                context.startActivity(
                    Intent.createChooser(shareIntent, "Share file via")
                )
            }
        }
    }
}