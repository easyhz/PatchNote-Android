package com.easyhz.patchnote.ui.screen.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.core.designSystem.component.button.BasicRadioButton
import com.easyhz.patchnote.core.designSystem.component.datePicker.BasicDatePicker
import com.easyhz.patchnote.core.designSystem.component.scaffold.PatchNoteScaffold
import com.easyhz.patchnote.core.designSystem.component.textField.BaseTextField
import com.easyhz.patchnote.core.designSystem.component.topbar.TopBar
import com.easyhz.patchnote.core.designSystem.util.extension.noRippleClickable
import com.easyhz.patchnote.core.designSystem.util.topbar.TopBarType
import com.easyhz.patchnote.core.model.filter.FilterProgress
import com.easyhz.patchnote.core.model.filter.FilterType
import com.easyhz.patchnote.core.model.filter.FilterValue
import com.easyhz.patchnote.core.model.filter.FilterValue.Companion.asInt
import com.easyhz.patchnote.core.model.filter.FilterValue.Companion.asLong
import com.easyhz.patchnote.core.model.filter.FilterValue.Companion.asString
import com.easyhz.patchnote.ui.screen.defect.DefectViewModel
import com.easyhz.patchnote.ui.screen.defect.contract.DefectIntent
import com.easyhz.patchnote.ui.screen.defect.contract.DefectSideEffect
import com.easyhz.patchnote.ui.screen.defectEntry.component.DefectCategoryField
import com.easyhz.patchnote.ui.screen.filter.contract.FilterIntent
import com.easyhz.patchnote.ui.screen.filter.contract.FilterSideEffect
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Primary

@Composable
fun FilterScreen(
    modifier: Modifier = Modifier,
    viewModel: FilterViewModel = hiltViewModel(),
    defectViewModel: DefectViewModel = hiltViewModel(),
    navigateToUp: () -> Unit,
    navigateToHome: (LinkedHashMap<String, String>) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val defectState by defectViewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    PatchNoteScaffold(
        topBar = {
            TopBar(
                left = TopBarType.TopBarIconButton(
                    iconId = R.drawable.ic_close_leading,
                    iconAlignment = Alignment.CenterStart,
                    tint = MainText,
                    onClick = { viewModel.postIntent(FilterIntent.NavigateToUp) }
                ),
                title = TopBarType.TopBarTitle(
                    stringId = R.string.filter_title
                ),
                right = TopBarType.TopBarTextButton(
                    stringId = R.string.filter_apply,
                    textAlignment = Alignment.CenterEnd,
                    textColor = Primary,
                    onClick = { defectViewModel.postIntent(DefectIntent.SearchItem) }
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
                when(filter.filterType) {
                    FilterType.RADIO -> {
                        BasicRadioButton(
                            modifier = Modifier.fillMaxWidth().heightIn(min = 40.dp),
                            title = stringResource(id = filter.nameId),
                            items = enumValues<FilterProgress>(),
                            selected = filterValue.asInt(),
                            onClick = {
                                viewModel.postIntent(
                                    FilterIntent.ChangeFilterValue(
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
                                    FilterIntent.ChangeFilterValue(
                                        filter = filter,
                                        value = FilterValue.StringValue(it)
                                    )
                                )
                            },
                            title = stringResource(id = filter.nameId),
                            placeholder = stringResource(id = R.string.filter_name_placeholder),
                            singleLine = true,
                            isFilled = false,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Next,
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { }
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
                                FilterIntent.ChangeFilterValue(
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
    }

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when(sideEffect) {
            is FilterSideEffect.NavigateToHome -> { navigateToHome(sideEffect.searchFieldParam) }
            is FilterSideEffect.ClearFocus -> { focusManager.clearFocus() }
            is FilterSideEffect.NavigateToUp -> { navigateToUp() }
        }
    }

    defectViewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when(sideEffect) {
            is DefectSideEffect.SearchItem -> { viewModel.postIntent(FilterIntent.Search(sideEffect.item)) }
            else -> { }
        }
    }
}