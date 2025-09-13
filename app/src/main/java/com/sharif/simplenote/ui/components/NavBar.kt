package com.sharif.simplenote.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sharif.simplenote.ui.theme.AppTypography
import com.sharif.simplenote.ui.theme.NeutralBlack
import com.sharif.simplenote.ui.theme.NeutralLightGrey
import com.sharif.simplenote.ui.theme.NeutralWhite
import com.sharif.simplenote.ui.theme.PrimaryBase

enum class RightActionType {
    Button, Icon, None
}


@Composable
fun NavBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    showTitle: Boolean = true,
    showRight: Boolean = false,
    rightActionType: RightActionType = RightActionType.None,
    rightActionIcon: ImageVector? = null,
    rightActionText: String = "",
    onRightActionClick: () -> Unit = {},
    backButtonText: String = "Back",
    onBackClick: () -> Unit,
    borderBottom: Boolean = false
) {
    val textColor = NeutralBlack
    val iconColor = PrimaryBase


    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Color.Transparent)
            .then(
                if (borderBottom) {
                    Modifier.drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        val y = size.height - strokeWidth / 2
                        drawLine(
                            color = NeutralLightGrey,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth
                        )
                    }
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 1.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Link(
                text = backButtonText,
                onClick = onBackClick,
                type = LinkType.NoUnderline,
                size = LinkSize.Large,
                icon = true,
                iconImage = Icons.Default.ArrowBack,
                iconPosition = LinkIconPosition.Left,
                state = LinkState.Normal,
            )


            // Title (center)
            if (showTitle && title != null) {
                Text(
                    text = title,
                    style = AppTypography.textBaseMedium,
                    color = textColor,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.weight(2f)
                )
            } else {
                Spacer(modifier = Modifier.weight(2f))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.weight(1f)
            ) {
                // Right action (right side)
                if (showRight) {
                    when (rightActionType) {
                        RightActionType.Button -> {
                            CustomButton(
                                text = rightActionText,
                                type = ButtonType.Primary,
                                onClick = onRightActionClick,
                                size = ButtonSize.Small,
                                modifier = Modifier.weight(1.5f)
                            )
                        }

                        RightActionType.Icon -> {
                            if (rightActionIcon != null) {
                                IconButton(
                                    onClick = onRightActionClick,
                                ) {
                                    Icon(
                                        imageVector = rightActionIcon,
                                        contentDescription = rightActionText,
                                        tint = iconColor
                                    )
                                }
                            }
                        }

                        RightActionType.None -> {
                            // No right action
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CustomNavBarPreview() {
    Column {

        // Nav bar with icon right action
        NavBar(
            title = "Edit Profile",
            showTitle = true,
            showRight = true,
            rightActionType = RightActionType.Icon,
            rightActionIcon = Icons.Default.Share,
            rightActionText = "Save",
            onRightActionClick = {},
            backButtonText = "Cancel",
            onBackClick = {}
        )

        // Nav bar with button right action and border
        NavBar(
            title = "Create Note",
            showTitle = true,
            showRight = true,
            rightActionType = RightActionType.Button,
            rightActionText = "Done",
            onRightActionClick = {},
            backButtonText = "Back",
            onBackClick = {},
            borderBottom = true
        )

        // Nav bar without title
        NavBar(
            showTitle = false,
            showRight = true,
            rightActionType = RightActionType.Icon,
            rightActionIcon = Icons.Default.Search,
            rightActionText = "Search",
            onRightActionClick = {},
            backButtonText = "Back",
            onBackClick = {}
        )

    }
}