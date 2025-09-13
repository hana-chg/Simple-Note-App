package com.sharif.simplenote.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.sharif.simplenote.ui.theme.NeutralWhite

@Composable
fun TabBar(
    selectedTab: IconMenuType = IconMenuType.Home,
    onTabSelected: (IconMenuType) -> Unit,
    onCenterButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(124.dp)
    ) {
        // White box for tab items
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(84.dp)
                .align(Alignment.BottomCenter),
            color = NeutralWhite,) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Home tab on the left
                IconMenu(
                    type = IconMenuType.Home,
                    state = if (selectedTab == IconMenuType.Home) IconMenuState.Active else IconMenuState.Default,
                    onClick = { onTabSelected(IconMenuType.Home) },
                )

                // Spacer for center button area
                Box(
                    modifier = Modifier
                        .size(80.dp)
                )

                // Settings tab on the right
                IconMenu(
                    type = IconMenuType.Setting,
                    state = if (selectedTab == IconMenuType.Setting) IconMenuState.Active else IconMenuState.Default,
                    onClick = { onTabSelected(IconMenuType.Setting) },
                )
            }
        }

        // Center floating button
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-0).dp)
                .zIndex(1f) // Ensure the button is above the white box
        ) {
            IconButton(
                onClick = onCenterButtonClick,
                style = IconButtonStyle.Border,
                icon = Icons.Default.Add,
                iconSize = 32.dp,
                buttonSize = 80.dp,
                modifier = Modifier
                    .size(80.dp)
            )
        }
    }
}




// Preview
@Preview
@Composable
fun BottomTabBarPreview() {

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Text(
                text = "Content Area",
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Bottom tab bar
        TabBar(
            onTabSelected = {},
            onCenterButtonClick = { /* Add new item */ }
        )
    }
}