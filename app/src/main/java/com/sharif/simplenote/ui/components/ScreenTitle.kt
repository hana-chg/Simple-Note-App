package com.sharif.simplenote.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sharif.simplenote.ui.theme.AppTypography
import com.sharif.simplenote.ui.theme.NeutralDarkGrey

@Composable
fun ScreenTitle(
    title: String = "",
    description: String = "",
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(328.dp)
            .height(76.dp)
            .background(Color.Transparent),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Start,
            style = AppTypography.text2xiBold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = description,
            textAlign = TextAlign.Start,
            style = AppTypography.textBaseRegular,
            overflow = TextOverflow.Ellipsis,
            color = NeutralDarkGrey,
            maxLines = 2,
            modifier = Modifier.padding(bottom = 4.dp)
        )

    }

}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    ScreenTitle("Title", "description")
}