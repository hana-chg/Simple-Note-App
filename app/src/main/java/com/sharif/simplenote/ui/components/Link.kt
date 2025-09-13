package com.sharif.simplenote.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sharif.simplenote.ui.theme.AppTypography
import com.sharif.simplenote.ui.theme.PrimaryBase
import com.sharif.simplenote.ui.theme.PrimaryDark
import com.sharif.simplenote.ui.theme.NeutralBaseGrey

enum class LinkType {
    Underline, NoUnderline
}

enum class LinkSize {
    Small, Large
}

enum class LinkIconPosition {
    Left, Right, None
}

enum class LinkState {
    Normal, Pressed, Disabled
}


@Composable
fun Link(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    type: LinkType = LinkType.Underline,
    size: LinkSize = LinkSize.Large,
    icon: Boolean = false,
    iconPosition: LinkIconPosition = LinkIconPosition.Right,
    state: LinkState = LinkState.Normal,
    enabled: Boolean = true ,
    iconImage: ImageVector = Icons.AutoMirrored.Filled.ArrowForward,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val currentState = when {
        !enabled -> LinkState.Disabled
        isPressed -> LinkState.Pressed
        else -> state
    }

    // Colors based on state
    val linkColor = when (currentState) {
        LinkState.Normal -> PrimaryBase
        LinkState.Pressed -> PrimaryDark
        LinkState.Disabled -> NeutralBaseGrey
    }

    // Text style based on size
    val textStyle = when (size) {
        LinkSize.Small -> AppTypography.text2xsMedium
        LinkSize.Large -> AppTypography.textBaseMedium
    }

    // Underline based on type
    val textDecoration = when (type) {
        LinkType.Underline -> TextDecoration.Underline
        LinkType.NoUnderline -> TextDecoration.None
    }

    // Icon size based on text size
    val iconSize: Dp = when (size) {
        LinkSize.Small -> 16.dp
        LinkSize.Large -> 20.dp
    }

    val content = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = linkColor,
                textDecoration = textDecoration
            )
        ) {
            append(text)
        }
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left icon (for SideLeft and Left positions)
        if (icon && (iconPosition == LinkIconPosition.Left)) {
            Icon(
                imageVector = iconImage,
                contentDescription = null,
                tint = linkColor,
                modifier = Modifier.size(iconSize)
            )
            Spacer(modifier = Modifier.width(4.dp))
        }

        // Text content
        Text(
            text = content,
            style = textStyle,
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            )
        )

        // Right icon
        if (icon && iconPosition == LinkIconPosition.Right) {
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = linkColor,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}


@Preview
@Composable
fun LinkExamples() {

        Link(
            text = "Forgot password?",
            onClick = { /* action */ },
            type = LinkType.Underline,
            icon = true,
            iconPosition = LinkIconPosition.Right
        )
}