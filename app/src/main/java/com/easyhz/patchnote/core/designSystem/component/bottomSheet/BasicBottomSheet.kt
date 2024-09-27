package com.easyhz.patchnote.core.designSystem.component.bottomSheet

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.ui.theme.DimColor
import com.easyhz.patchnote.ui.theme.MainBackground
import com.easyhz.patchnote.ui.theme.MainText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    dragHandle: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        containerColor = MainBackground,
        contentColor = MainText,
        scrimColor = DimColor,
        dragHandle = dragHandle,
        contentWindowInsets = { BottomSheetDefaults.windowInsets.only(WindowInsetsSides.Bottom) },
    ) {
        content()
        Spacer(modifier = Modifier.navigationBarsPadding())
    }
}