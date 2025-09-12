// components/StatusBar.kt
package com.sharif.simplenote.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sharif.simplenote.ui.theme.PrimaryBase

@Composable
fun StatusBarSpacer(height: Dp = 24.dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    )
}

@Composable
fun SetupStatusBar(color: Color = PrimaryBase, darkIcons: Boolean = true) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = color,
            darkIcons = darkIcons
        )
    }
}

@Composable
fun WithStatusBarPadding(
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.statusBarsPadding()) {
        content()
    }
}