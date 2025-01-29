package com.easyhz.patchnote.core.designSystem.component.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.designSystem.util.extension.circleClickable
import com.easyhz.patchnote.core.designSystem.util.extension.noRippleClickable
import com.easyhz.patchnote.ui.theme.SemiBold24

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.app_name_ko),
    onClickName: () -> Unit = { },
    onClickSetting: () -> Unit,
    onClickExport: () -> Unit
) {
    Row(
        modifier = modifier.height(52.dp).fillMaxWidth().padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.noRippleClickable { onClickName() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.widthIn(max = 200.dp),
                text = title,
                style = SemiBold24,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Icon(
                painter = painterResource(R.drawable.ic_info),
                contentDescription = null
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(32.dp).circleClickable(
                    onClick = onClickExport
                ),
                painter = painterResource(R.drawable.ic_export),
                contentDescription = null
            )
            Icon(
                modifier = Modifier.size(32.dp).circleClickable(
                    onClick = { onClickSetting() }
                ),
                painter = painterResource(R.drawable.ic_setting),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeTopBarPreview() {
    HomeTopBar(
        title = "patchnote",
        onClickName = { },
        onClickSetting = { },
        onClickExport = { }
    )
}