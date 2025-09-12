package com.sharif.simplenote.ui.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sharif.simplenote.R
import com.sharif.simplenote.ui.components.ButtonSize
import com.sharif.simplenote.ui.components.ButtonType
import com.sharif.simplenote.ui.components.CustomButton
import com.sharif.simplenote.ui.theme.NeutralWhite
import com.sharif.simplenote.ui.theme.PrimaryBase
import com.sharif.simplenote.ui.theme.interFamily

@Composable
fun OnboardingScreen(navController: NavController? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBase)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.5f))

        // Main content column
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(2f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.onboarding),
                contentDescription = "Onboarding Image",
                modifier = Modifier
                    .size(280.dp)
                    .padding(bottom = 32.dp)
            )

            Text(
                text = stringResource(R.string.onboarding_message),
                fontFamily = interFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = NeutralWhite,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 48.dp)
        ) {
            CustomButton(
                text = "Get Started",
                type = ButtonType.Secondary,
                onClick = {
                    // Navigate to next screen
                    navController?.navigate("login")
                },
                icon = true,
                iconPosition = "Right",
                enabled = true,
                size = ButtonSize.Block
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen()
}