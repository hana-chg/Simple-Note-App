package com.sharif.simplenote.ui.screen.setting

import SettingOption
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.sharif.simplenote.viewModel.SettingViewModel
import com.sharif.simplenote.R
import com.sharif.simplenote.ui.components.NavBar
import com.sharif.simplenote.ui.components.StatusBar
import com.sharif.simplenote.ui.navigation.NavItem
import com.sharif.simplenote.ui.theme.ErrorBase
import com.sharif.simplenote.ui.theme.NeutralLightGrey
import com.sharif.simplenote.ui.theme.NeutralWhite
import androidx.compose.runtime.collectAsState



@Composable
fun SettingScreen(
    navController: NavHostController, viewModel: SettingViewModel
) {
    val userState by viewModel.userState.collectAsState()
    var username = ""
    var email = ""

    LaunchedEffect(Unit) {
        viewModel.getUserInfo()
    }


    userState.user?.let { user ->
        username = user.username
        email = user.email
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NeutralWhite),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Status Bar
        StatusBar()

        // NavBar
        NavBar(
            modifier = Modifier,
            showTitle = true,
            title = "Settings",
            onBackClick = {
                navController.navigate(NavItem.Home.route) {
                    popUpTo(NavItem.Setting.route) { inclusive = true }
                }
            },
            borderBottom = true
        )

        // Content
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Spacer(modifier = Modifier.height(16.dp))

            // User Profile Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Profile Image
                Image(
                    painter = painterResource(R.drawable.default_pfp),
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.size(16.dp))

                // User Info
                Column {
                    Text(
                        text = username,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.size(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email",
                            modifier = Modifier.size(16.dp)
                        )

                        Spacer(modifier = Modifier.size(8.dp))

                        Text(
                            text = email,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }

            // Divider
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = 1.dp,
                color = NeutralLightGrey
            )

            // App Settings Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "APP SETTINGS",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                SettingOption(
                    Icons.Outlined.Lock, title = "Change Password",
                    onClick = {/*TODO*/ }
                )

                // Divider
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    thickness = 1.dp,
                    color = NeutralLightGrey
                )

                SettingOption(
                    Icons.AutoMirrored.Outlined.ExitToApp, title = "Logout",
                    onClick = {
                        viewModel.logout()
                        navController.navigate(NavItem.Login.route) {
                            popUpTo(NavItem.Setting.route) { inclusive = true }
                        }
                    }, ErrorBase
                )
            }
        }
    }
}

