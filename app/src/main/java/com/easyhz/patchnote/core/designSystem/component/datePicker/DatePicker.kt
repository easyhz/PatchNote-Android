package com.easyhz.patchnote.core.designSystem.component.datePicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.DateFormatUtil
import com.easyhz.patchnote.core.designSystem.component.textField.TextFieldContainerTitle
import com.easyhz.patchnote.core.designSystem.util.extension.noRippleClickable
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Medium18
import com.easyhz.patchnote.ui.theme.PlaceholderText
import com.easyhz.patchnote.ui.theme.Primary
import com.easyhz.patchnote.ui.theme.SemiBold16
import com.easyhz.patchnote.ui.theme.SubBackground
import com.easyhz.patchnote.ui.theme.SubText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicDatePicker(
    modifier: Modifier = Modifier,
    spacing: Dp = 12.dp,
    title: String? = null,
    selectedDate: Long?,
    onDateSelected: (Long?) -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    LaunchedEffect(selectedDate) {
        if (selectedDate == datePickerState.selectedDateMillis) return@LaunchedEffect
        datePickerState.selectedDateMillis = selectedDate
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        title?.let {
            TextFieldContainerTitle(
                title = title,
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(SubBackground)
                .noRippleClickable { showDatePicker = true }
                .padding(vertical = 8.dp)
                .padding(start = 12.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = selectedDate?.let { DateFormatUtil.convertMillisToDate(it) }
                    ?: stringResource(R.string.date_picker_placeholder),
                style = Medium18,
                color = if (selectedDate != null) MainText else PlaceholderText
            )
            Box(
                modifier = Modifier
                    .sizeIn(minWidth = 32.dp, minHeight = 32.dp)
                    .noRippleClickable {
                        onDateSelected(null)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = "delete",
                    tint = PlaceholderText
                )
            }
        }
    }
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            shape = RoundedCornerShape(12.dp),
            colors = DatePickerDefaults.colors(
                containerColor = MainBackground,
                titleContentColor = MainText,
                headlineContentColor = MainText,
                weekdayContentColor = PlaceholderText,
                subheadContentColor = PlaceholderText,
                navigationContentColor = Primary,
                yearContentColor = SubText,
                disabledYearContentColor = PlaceholderText,
                currentYearContentColor = Primary,
                selectedYearContentColor = MainBackground,
                disabledSelectedYearContentColor = PlaceholderText,
                selectedYearContainerColor = Primary,
                disabledSelectedYearContainerColor = PlaceholderText,
                dayContentColor = MainText,
                disabledDayContentColor = PlaceholderText,
                selectedDayContentColor = MainBackground,
                disabledSelectedDayContentColor = PlaceholderText,
                selectedDayContainerColor = Primary,
                disabledSelectedDayContainerColor = Primary,
                todayContentColor = Primary,
                todayDateBorderColor = Primary,
                dayInSelectionRangeContentColor = MainText,
                dayInSelectionRangeContainerColor = SubBackground,
                dividerColor = SubText,
            ),
            confirmButton = { },
            dismissButton = { }
        ) {
            Column(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                DatePicker(
                    state = datePickerState,
                    showModeToggle = false,
                    title = null,
                    headline = null,
                    colors = DatePickerDefaults.colors(
                        containerColor = MainBackground,
                        titleContentColor = MainText,
                        headlineContentColor = MainText,
                        weekdayContentColor = PlaceholderText,
                        subheadContentColor = PlaceholderText,
                        navigationContentColor = Primary,
                        yearContentColor = SubText,
                        disabledYearContentColor = PlaceholderText,
                        currentYearContentColor = Primary,
                        selectedYearContentColor = MainBackground,
                        disabledSelectedYearContentColor = PlaceholderText,
                        selectedYearContainerColor = Primary,
                        disabledSelectedYearContainerColor = PlaceholderText,
                        dayContentColor = MainText,
                        disabledDayContentColor = PlaceholderText,
                        selectedDayContentColor = MainBackground,
                        disabledSelectedDayContentColor = PlaceholderText,
                        selectedDayContainerColor = Primary,
                        disabledSelectedDayContainerColor = Primary,
                        todayContentColor = Primary,
                        todayDateBorderColor = Primary,
                        dayInSelectionRangeContentColor = MainText,
                        dayInSelectionRangeContainerColor = SubBackground,
                        dividerColor = Color.Transparent,
                    ),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DatePickerButton(
                        string = stringResource(R.string.date_picker_dismiss),
                        textColor = SubText,
                        onClick = { showDatePicker = false }
                    )
                    DatePickerButton(
                        string = stringResource(R.string.date_picker_confirm),
                        textColor = Primary,
                        onClick = {
                            onDateSelected(datePickerState.selectedDateMillis)
                            showDatePicker = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun DatePickerButton(
    modifier: Modifier = Modifier,
    string: String,
    textColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .heightIn(min = 36.dp)
            .widthIn(min = 32.dp)
            .clip(RoundedCornerShape(4.dp))
            .noRippleClickable { onClick() }
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = string,
            style = SemiBold16,
            color = textColor
        )
    }
}

@Preview
@Composable
private fun BasicDatePickerPreview() {
    var date: Long? by remember { mutableStateOf(null) }
    BasicDatePicker(selectedDate = date, onDateSelected = {
        date = it
    }, title = "작업일")

}