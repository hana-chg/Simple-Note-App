package com.sharif.simplenote.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sharif.simplenote.R
import com.sharif.simplenote.ui.theme.*

enum class InputFieldState {
    Default, Focused, Error, Filled, Disabled
}

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    caption: Boolean = false,
    label: Boolean = false,
    labelText: String = "",
    state: InputFieldState = InputFieldState.Default,
    placeholderText: String = "",
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    maxLines: Int = 1,
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    // Determine current state
    val currentState = when {
        !enabled -> InputFieldState.Disabled
        state == InputFieldState.Error -> InputFieldState.Error
        isFocused -> InputFieldState.Focused
        value.isNotEmpty() -> InputFieldState.Filled
        else -> InputFieldState.Default
    }

    // Colors based on state
    val (backgroundColor, textColor, captionAndBorderColor) = when (currentState) {
        InputFieldState.Default -> Triple(NeutralWhite, NeutralBaseGrey, NeutralBaseGrey)
        InputFieldState.Focused -> Triple(NeutralWhite, NeutralBlack, PrimaryBase)
        InputFieldState.Error -> Triple(NeutralWhite, NeutralBlack, ErrorBase)
        InputFieldState.Filled -> Triple(NeutralWhite, NeutralBlack, NeutralBaseGrey)
        InputFieldState.Disabled -> Triple(NeutralLightGrey, NeutralBaseGrey, NeutralLightGrey)
    }



    Column(modifier = modifier) {
        // Label
        if (label && labelText.isNotEmpty()) {
            Text(
                text = labelText,
                color = NeutralBlack,
                style = AppTypography.textBaseMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(8))
                .border(
                    width = 2.dp,
                    color = captionAndBorderColor,
                    shape = RoundedCornerShape(8.dp)
                )
                .background(backgroundColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Text field
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .weight(1f)
                        .onFocusChanged { },
                    enabled = enabled,
                    singleLine = singleLine,
                    maxLines = maxLines,
                    textStyle = TextStyle(
                        color = textColor,
                        fontSize = 16.sp
                    ),
                    visualTransformation = if (isPassword && !passwordVisible) {
                        PasswordVisualTransformation()
                    } else {
                        VisualTransformation.None
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                    cursorBrush = SolidColor(PrimaryBase),
                    interactionSource = interactionSource,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholderText,
                                    color = NeutralBaseGrey,
                                    style = AppTypography.textBaseRegular
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                // Trailing icon (for password visibility toggle)
                if (isPassword) {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector =
                                if (passwordVisible)
                                    ImageVector.vectorResource(
                                        R.drawable.eye
                                    )
                                else ImageVector.vectorResource(
                                    R.drawable.eye_off
                                ),
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                }
            }
        }

        // Caption
        if (caption) {
            val captionText = when (currentState) {
                InputFieldState.Error -> "This field is required"
                else -> "Help text"
            }

            Text(
                text = captionText,
                color = captionAndBorderColor,
                style = AppTypography.text2xsRegular,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun CustomTextFieldPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Default state
        InputField(
            value = "",
            onValueChange = {},
            placeholderText = "Enter your name",
            label = true,
            labelText = "Name",
            caption = true
        )

        // Focused state
        InputField(
            value = "John Doe",
            onValueChange = {},
            label = true,
            labelText = "Email",
            caption = true,
            state = InputFieldState.Focused
        )

        // Error state
        InputField(
            value = "",
            onValueChange = {},
            placeholderText = "Enter password",
            label = true,
            labelText = "Password",
            isPassword = true,
            caption = true,
            state = InputFieldState.Error
        )

        // Disabled state
        InputField(
            value = "Disabled text",
            onValueChange = {},
            label = true,
            labelText = "Disabled Field",
            caption = true,
            enabled = false
        )
    }
}