package com.sharif.simplenote.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sharif.simplenote.ui.theme.*

enum class IconButtonStyle {
    Border, Primary, Secondary
}

enum class IconButtonState {
    Normal, Pressed, Disabled
}

@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: IconButtonStyle = IconButtonStyle.Primary,
    state: IconButtonState = IconButtonState.Normal,
    icon: ImageVector = Icons.Default.Add,
    iconSize: Dp = 32.dp,
    buttonSize: Dp = 64.dp,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val currentState = when {
        !enabled -> IconButtonState.Disabled
        isPressed -> IconButtonState.Pressed
        else -> state
    }

    val (containerColor, contentColor, elevation) = when (style) {
        IconButtonStyle.Border -> when (currentState) {
            IconButtonState.Normal -> Triple(
                PrimaryBase,
                NeutralWhite,
                8.dp
            )

            IconButtonState.Pressed -> Triple(
                PrimaryDark,
                NeutralWhite,
                4.dp
            )

            IconButtonState.Disabled -> Triple(
                NeutralBaseGrey,
                NeutralLightGrey,
                4.dp
            )
        }

        IconButtonStyle.Primary -> when (currentState) {
            IconButtonState.Normal -> Triple(
                PrimaryBase,
                NeutralWhite,
                0.dp
            )

            IconButtonState.Pressed -> Triple(
                PrimaryDark,
                NeutralWhite,
                0.dp
            )

            IconButtonState.Disabled -> Triple(
                NeutralBaseGrey,
                NeutralLightGrey,
                0.dp
            )
        }

        IconButtonStyle.Secondary -> when (currentState) {
            IconButtonState.Normal -> Triple(
                NeutralWhite,
                PrimaryBase,
                0.dp
            )

            IconButtonState.Pressed -> Triple(
                PrimaryLight,
                PrimaryBase,
                0.dp
            )

            IconButtonState.Disabled -> Triple(
                NeutralBaseGrey,
                NeutralLightGrey,
                0.dp
            )
        }
    }

    Box(
        modifier = modifier
            .size(buttonSize)
            .shadow(
                elevation = elevation,
                shape = CircleShape,
                clip = true,
            )
            .clip(CircleShape)
            .background(containerColor)
            .clickable(
                onClick = onClick,
                enabled = enabled && state != IconButtonState.Disabled,
                interactionSource = interactionSource
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(iconSize)
        )
    }
}

@Preview
@Composable
fun IconCircleButtonPreview() {
    Column {
        // Border style
        Row {
            IconButton(
                onClick = {},
                style = IconButtonStyle.Border,
                state = IconButtonState.Normal
            )
            IconButton(
                onClick = {},
                style = IconButtonStyle.Border,
                state = IconButtonState.Pressed
            )
            IconButton(
                onClick = {},
                style = IconButtonStyle.Border,
                state = IconButtonState.Disabled,
                enabled = false
            )
        }

        // Primary style
        Row {
            IconButton(
                onClick = {},
                style = IconButtonStyle.Primary,
                state = IconButtonState.Normal
            )
            IconButton(
                onClick = {},
                style = IconButtonStyle.Primary,
                state = IconButtonState.Pressed
            )
            IconButton(
                onClick = {},
                style = IconButtonStyle.Primary,
                state = IconButtonState.Disabled,
                enabled = false
            )
        }

    }
}