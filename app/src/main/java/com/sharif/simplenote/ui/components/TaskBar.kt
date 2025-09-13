package com.sharif.simplenote.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sharif.simplenote.R
import com.sharif.simplenote.ui.theme.AppTypography
import com.sharif.simplenote.ui.theme.NeutralLightGrey
import com.sharif.simplenote.ui.theme.PrimaryBase

@Composable
fun TaskBar(
    modifier: Modifier = Modifier,
    status: String,
    onDeleteClick: () -> Unit = {},
    borderColor: Color = NeutralLightGrey,
    icon: ImageVector = ImageVector.vectorResource(R.drawable.trash),
    iconTint: Color = Color.White,
    buttonBackground: Color = PrimaryBase
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Status indicator
            Text(
                text = status,
                style = AppTypography.text2xsRegular,
                modifier = Modifier.padding(5.dp)
            )

            // Delete button
            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier
                    .size(48.dp)
                    .background(buttonBackground)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Delete",
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun TaskBarPreview() {
    Surface {
        TaskBar(
            status = "Design app icon",
            onDeleteClick = { println("Delete clicked") }
        )
    }
}