package com.sharif.simplenote.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sharif.simplenote.R
import com.sharif.simplenote.ui.theme.AppTypography
import com.sharif.simplenote.ui.theme.NeutralDarkGrey
import com.sharif.simplenote.ui.theme.PrimaryBase

enum class IconMenuType {
    Home, Search, Setting, Finished
}

enum class IconMenuState {
    Default, Active
}

@Composable
fun IconMenu(
    type: IconMenuType,
    state: IconMenuState = IconMenuState.Default,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (icon, contentDescription, label) = when (type) {
        IconMenuType.Home -> Triple(
            if (state == IconMenuState.Active) ImageVector.vectorResource(R.drawable.home_filled)
            else ImageVector.vectorResource(R.drawable.home_outlined),
            "Home", "Home"
        )

        IconMenuType.Search -> Triple(
            if (state == IconMenuState.Active) ImageVector.vectorResource(R.drawable.search_filled)
            else ImageVector.vectorResource(R.drawable.search_outlined),
            "Search", "Search"
        )

        IconMenuType.Setting -> Triple(
            if (state == IconMenuState.Active) ImageVector.vectorResource(R.drawable.cog_filled)
            else ImageVector.vectorResource(R.drawable.cog_outlined),
            "Settings", "Settings"
        )

        IconMenuType.Finished -> Triple(
            if (state == IconMenuState.Active) ImageVector.vectorResource(R.drawable.finished_filled)
            else ImageVector.vectorResource(R.drawable.finished_outlined),
            "Finished", "Finished"
        )
    }

    val color = when (state) {
        IconMenuState.Default -> NeutralDarkGrey
        IconMenuState.Active -> PrimaryBase
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(8.dp)
            .clickable(
                onClick = onClick,
            )
            .width(52.dp)
            .height(52.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = color,
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            style = AppTypography.textXsRegular,
            color = color,
            textAlign = TextAlign.Center
        )
    }
}



@Preview
@Composable
fun IconMenuWithLabelExamples() {
    Column {
        // Individual menu items with labels
        Row {
            IconMenu(
                type = IconMenuType.Home,
                state = IconMenuState.Default,
                onClick = {}
            )
            Spacer(modifier = Modifier.width(16.dp))
            IconMenu(
                type = IconMenuType.Home,
                state = IconMenuState.Active,
                onClick = {}
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}