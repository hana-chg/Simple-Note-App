package com.sharif.simplenote.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sharif.simplenote.R
import com.sharif.simplenote.ui.theme.NeutralBlack
import com.sharif.simplenote.ui.theme.NeutralDarkGrey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun StatusBar(
    modifier: Modifier = Modifier,
    statusBarColor: Color = Color.Transparent,
    darkIcons: Boolean = true
) {
    val systemUiController = rememberSystemUiController()

    // Set the status bar color
    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = darkIcons
        )
    }
}

