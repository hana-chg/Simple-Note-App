package com.sharif.simplenote.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sharif.simplenote.ui.theme.AppTypography

@Composable
fun SectionTitle(
    label: String,
    linkText: String? = null,
    onLinkClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    showLinkIcon: Boolean = false,
    linkType: LinkType = LinkType.Underline,
    linkSize: LinkSize = LinkSize.Small
) {
    Row(
        modifier = modifier
            .width(360.dp)
            .height(20.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (linkText == null) Arrangement.Center else Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = AppTypography.textSmBold,
            modifier = Modifier.weight(1f, fill = false)
        )

        if (linkText != null) {
            Link(
                text = linkText,
                onClick = onLinkClick,
                icon = showLinkIcon,
                iconPosition = LinkIconPosition.Right,
                type = linkType,
                size = linkSize,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun SectionTitlePreview() {
    SectionTitle(
        label = "Pinned Notes",
        linkText = "View all",
        onLinkClick = {  },
        linkSize = LinkSize.Small,
        linkType = LinkType.NoUnderline
    )
}