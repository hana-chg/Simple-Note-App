package com.sharif.simplenote.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sharif.simplenote.ui.theme.AppTypography
import com.sharif.simplenote.ui.theme.NeutralBaseGrey
import com.sharif.simplenote.ui.theme.NeutralLightGrey
import com.sharif.simplenote.ui.theme.NeutralWhite
import com.sharif.simplenote.ui.theme.PrimaryBase
import com.sharif.simplenote.ui.theme.PrimaryDark
import com.sharif.simplenote.ui.theme.PrimaryLight

enum class ButtonType {
    Primary, Secondary, Transparent
}

enum class ButtonState {
    Normal, Disabled, Pressed
}

enum class ButtonSize {
    Small, Large, Block
}

@Composable
fun CustomButton(
    text: String,
    type: ButtonType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Boolean = false,
    iconPosition: String = "Right",
    enabled: Boolean = true,
    size: ButtonSize = ButtonSize.Block
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val buttonState = when {
        !enabled -> ButtonState.Disabled
        isPressed -> ButtonState.Pressed
        else -> ButtonState.Normal
    }

    val (backgroundColor, contentColor, borderColor) = when (type) {
        ButtonType.Primary -> when (buttonState) {
            ButtonState.Normal -> Triple(PrimaryBase, NeutralWhite, null)
            ButtonState.Disabled -> Triple(PrimaryDark, NeutralWhite, null)
            ButtonState.Pressed -> Triple(NeutralBaseGrey, NeutralLightGrey, null)
        }
        ButtonType.Secondary -> when (buttonState) {
            ButtonState.Normal -> Triple(NeutralWhite, PrimaryBase, PrimaryBase)
            ButtonState.Disabled -> Triple(NeutralWhite, NeutralBaseGrey,NeutralBaseGrey )
            ButtonState.Pressed -> Triple(PrimaryLight, PrimaryDark, PrimaryDark)
        }
        ButtonType.Transparent -> when(buttonState) {
            ButtonState.Normal -> Triple(Color.Transparent, PrimaryBase, Color.Transparent)
            ButtonState.Disabled -> Triple(Color.Transparent, NeutralBaseGrey, Color.Transparent)
            ButtonState.Pressed -> Triple(PrimaryLight, PrimaryBase, Color.Transparent)
        }
    }

    val (width, height, textStyle) = when (size) {
        ButtonSize.Block -> Triple(328.dp, 54.dp, AppTypography.textBaseMedium)
        ButtonSize.Large -> Triple(170.dp, 54.dp, AppTypography.textBaseMedium)
        ButtonSize.Small -> Triple(154.dp, 38.dp, AppTypography.textBaseMedium)
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .width(width)
            .height(height),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = backgroundColor,
            disabledContentColor = contentColor
        ),
        border = BorderStroke(1.dp, borderColor ?: Color.Transparent),
        shape = RoundedCornerShape(100.dp),
        enabled = enabled,
        interactionSource = interactionSource
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon && iconPosition == "Left") {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = text,
                style = textStyle,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            if (icon && iconPosition == "Right") {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}