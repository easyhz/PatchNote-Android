package com.easyhz.patchnote.core.designSystem.component.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.R
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.Medium18

@Composable
fun TeamCard(
    modifier: Modifier = Modifier,
    teamName: String,
    painter: Painter = painterResource(R.drawable.ic_right_arrow),
    color: Color = MainText,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .heightIn(min = 56.dp)
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = teamName,
            style = Medium18.copy(
                color = color
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Icon(
            painter = painter,
            contentDescription = null,
            tint = color
        )
    }
}

@Preview
@Composable
private fun TeamCardPreview() {
    TeamCard(
        teamName = "Team NameTeam NameTeam NameTeam NameTeam NameTeam NameTeam NameTeam NameTeam NameTeam NameTeam NameTeam NameTeam NameTeam NameTeam NameTeam Name",
        onClick = { }
    )
}