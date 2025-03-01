package com.easyhz.patchnote.ui.screen.defect.export

import android.Manifest
import android.content.ClipData
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.core.designSystem.component.button.BasicRadioButton
import com.easyhz.patchnote.core.designSystem.component.datePicker.BasicDatePicker
import com.easyhz.patchnote.core.designSystem.component.dialog.BasicDialog
import com.easyhz.patchnote.core.designSystem.component.loading.LoadingIndicator
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.textField.BaseTextField
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.dialog.BasicDialogButton
import com.easyhz.patchnote.core.designSystem.util.extension.noRippleClickable
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.core.model.filter.FilterParam
import com.easyhz.patchnote.core.model.filter.FilterProgress
import com.easyhz.patchnote.core.model.filter.FilterType
import com.easyhz.patchnote.core.model.filter.FilterValue
import com.easyhz.patchnote.core.model.filter.FilterValue.Companion.asInt
import com.easyhz.patchnote.core.model.filter.FilterValue.Companion.asLong
import com.easyhz.patchnote.core.model.filter.FilterValue.Companion.asString
import com.easyhz.patchnote.ui.screen.defect.common.DefectViewModel
import com.easyhz.patchnote.ui.screen.defect.common.contract.DefectIntent
import com.easyhz.patchnote.ui.screen.defect.common.contract.DefectSideEffect
import com.easyhz.patchnote.ui.screen.defect.defectEntry.component.DefectCategoryField
import com.easyhz.patchnote.ui.screen.defect.export.contract.DefectExportIntent
import com.easyhz.patchnote.ui.screen.defect.export.contract.DefectExportSideEffect
import com.easyhz.patchnote.ui.theme.LocalSnackBarHostState
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Primary
import com.easyhz.patchnote.ui.theme.SemiBold16
import com.easyhz.patchnote.ui.theme.SemiBold18
import com.easyhz.patchnote.ui.theme.SubBackground

@Composable
fun DefectExportScreen(
    modifier: Modifier = Modifier,
    viewModel: DefectExportViewModel = hiltViewModel(),
    defectViewModel: DefectViewModel = hiltViewModel(),
    filterParam: FilterParam,
    navigateToUp: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val defectState by defectViewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val snackBarHost = LocalSnackBarHostState.current

    LaunchedEffect(Unit) {
        viewModel.postIntent(DefectExportIntent.InitFilter(filterParam))
        defectViewModel.postIntent(DefectIntent.InitFilter(filterParam))
    }

    PatchNoteScaffold(
        topBar = {
            TopBar(
                left = TopBarType.TopBarIconButton(
                    iconId = R.drawable.ic_close_leading,
                    iconAlignment = Alignment.CenterStart,
                    tint = MainText,
                    onClick = { viewModel.postIntent(DefectExportIntent.NavigateToUp) }
                ),
                title = TopBarType.TopBarTitle(
                    stringId = R.string.defect_export_title
                ),
                right = TopBarType.TopBarTextButton(
                    stringId = R.string.defect_export_button,
                    textAlignment = Alignment.CenterEnd,
                    textColor = Primary,
                    onClick = { viewModel.postIntent(DefectExportIntent.ClickExport) }
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
                .padding(vertical = 8.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(32.dp)
                    .noRippleClickable {
                        viewModel.postIntent(DefectExportIntent.Reset)
                        defectViewModel.postIntent(DefectIntent.Reset)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_reset_leading),
                    contentDescription = "reset",
                )
                Text(
                    text = stringResource(id = R.string.filter_reset),
                    color = Primary,
                    style = SemiBold16,
                )
            }
            defectState.entryItem.forEach { (category, value) ->
                DefectCategoryField(
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
                    onIconClick = {
                        defectViewModel.postIntent(DefectIntent.ClearData(category))
                    },
                    onFocusChanged = {
                        defectViewModel.postIntent(
                            DefectIntent.ChangeFocusState(
                                categoryType = category,
                                focusState = it
                            )
                        )
                    },
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            }
            uiState.filterItem.forEach { (filter, filterValue) ->
                when (filter.filterType) {
                    FilterType.RADIO -> {
                        BasicRadioButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 40.dp),
                            title = stringResource(id = filter.nameId),
                            items = enumValues<FilterProgress>(),
                            selected = filterValue.asInt(),
                            onClick = {
                                viewModel.postIntent(
                                    DefectExportIntent.ChangeFilterValue(
                                        filter = filter,
                                        value = FilterValue.IntValue(it)
                                    )
                                )
                            }
                        )
                    }

                    FilterType.FREE_FORM -> {
                        BaseTextField(
                            containerModifier = Modifier.height(40.dp),
                            value = filterValue.asString(),
                            onValueChange = {
                                viewModel.postIntent(
                                    DefectExportIntent.ChangeFilterValue(
                                        filter = filter,
                                        value = FilterValue.StringValue(it)
                                    )
                                )
                            },
                            title = stringResource(id = filter.nameId),
                            placeholder = stringResource(id = R.string.filter_name_placeholder),
                            singleLine = true,
                            isFilled = false,
                            onIconClick = {
                                viewModel.postIntent(
                                    DefectExportIntent.ClearFilterValue(
                                        filter
                                    )
                                )
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Next,
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            )
                        )
                    }

                    FilterType.DATE -> {
                        BasicDatePicker(
                            modifier = Modifier.height(40.dp),
                            title = stringResource(id = filter.nameId),
                            selectedDate = filterValue.asLong(),
                        ) {
                            viewModel.postIntent(
                                DefectExportIntent.ChangeFilterValue(
                                    filter = filter,
                                    value = FilterValue.LongValue(it)
                                )
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.imePadding())
        }

        if(uiState.isShowExportDialog) {
            BasicDialog(
                title = stringResource(R.string.dialog_export_title),
                content = stringResource(R.string.dialog_export_content),
                positiveButton = BasicDialogButton(
                    text = stringResource(R.string.dialog_export_positive),
                    style = SemiBold18.copy(color = MainBackground),
                    backgroundColor = Primary,
                    onClick = { defectViewModel.postIntent(DefectIntent.SearchItem) }
                ),
                negativeButton = BasicDialogButton(
                    text = stringResource(R.string.dialog_negative_button),
                    backgroundColor = SubBackground,
                    onClick = { viewModel.postIntent(DefectExportIntent.HideExportDialog) }
                )
            )
        }

        if (uiState.isShowDateDialog) {
            BasicDialog(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.dialog_defect_export_need_date_title),
                content = stringResource(R.string.dialog_defect_export_need_date_content),
                positiveButton = BasicDialogButton(
                    text = stringResource(R.string.dialog_defect_export_need_date_button),
                    style = SemiBold18.copy(color = MainBackground),
                    backgroundColor = Primary,
                    onClick = { viewModel.postIntent(DefectExportIntent.HideDateDialog) }
                ),
                negativeButton = null
            )
        }
    }

    LoadingIndicator(
        isLoading = uiState.isLoading,
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            viewModel.postIntent(DefectExportIntent.ShowExportDialog)
        }
    }
    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is DefectExportSideEffect.ClearFocus -> {
                focusManager.clearFocus()
            }

            is DefectExportSideEffect.NavigateToUp -> {
                navigateToUp()
            }

            is DefectExportSideEffect.RequestPermission -> {
                val hasPermission = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED

                if (hasPermission) {
                    viewModel.postIntent(DefectExportIntent.ShowExportDialog)
                } else {
                    permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }
            is DefectExportSideEffect.ShareIntent -> {
                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.file_provider",
                    sideEffect.file
                )

                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "application/vnd.ms-excel"
                    type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                    putExtra(Intent.EXTRA_STREAM, uri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    clipData = ClipData.newRawUri("", uri);
                }
                viewModel.postIntent(DefectExportIntent.SetLoading(false))
                context.startActivity(
                    Intent.createChooser(shareIntent, sideEffect.file.name)
                )
            }
            is DefectExportSideEffect.ShowSnackBar -> {
                snackBarHost.showSnackbar(
                    message = sideEffect.value,
                    withDismissAction = true
                )
            }
        }
    }

    defectViewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is DefectSideEffect.SearchItem -> {
                viewModel.postIntent(DefectExportIntent.Export(sideEffect.item))
            }
            else -> {}
        }
    }
}