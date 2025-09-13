package com.sharif.simplenote.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sharif.simplenote.ui.theme.AppTypography
import com.sharif.simplenote.ui.theme.ErrorLight
import com.sharif.simplenote.ui.theme.NeutralBlack
import com.sharif.simplenote.ui.theme.NeutralWhite
import com.sharif.simplenote.ui.theme.PrimaryLight
import com.sharif.simplenote.ui.theme.SecondaryLight
import com.sharif.simplenote.ui.theme.SuccessLight
import com.sharif.simplenote.ui.theme.WarningDark


enum class NoteCardColor {
    Primary, Secondary, Error, Success, Warning, Neutral
}


@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    color: NoteCardColor = NoteCardColor.Secondary,
    onClick: (() -> Unit)? = null
) {
    val cardColors = when (color) {
        NoteCardColor.Primary -> CardDefaults.cardColors(
            containerColor = PrimaryLight,
            contentColor = NeutralBlack,
        )

        NoteCardColor.Secondary -> CardDefaults.cardColors(
            containerColor = SecondaryLight,
            contentColor = NeutralBlack
        )

        NoteCardColor.Error -> CardDefaults.cardColors(
            containerColor = ErrorLight,
            contentColor = NeutralBlack
        )

        NoteCardColor.Success -> CardDefaults.cardColors(
            containerColor = SuccessLight,
            contentColor = NeutralBlack
        )

        NoteCardColor.Warning -> CardDefaults.cardColors(
            containerColor = WarningDark,
            contentColor = NeutralBlack
        )

        NoteCardColor.Neutral -> CardDefaults.cardColors(
            containerColor = NeutralWhite,
            contentColor = NeutralBlack
        )
    }

    Card(
        colors = cardColors,
        modifier = modifier
            .fillMaxWidth()
            .height(244.dp)
            .clickable(enabled = onClick != null, onClick = { onClick?.invoke() }),
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = title,
                style = AppTypography.textBaseMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = content,
                style = AppTypography.textXsRegular,
                maxLines = 11,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .alpha(0.6f)
            )
        }
    }
}


@Preview
@Composable
fun BorderCardExample() {
    Column(Modifier.width(155.dp)) {
        NoteCard(
            title = "\uD83D\uDCA1 New Product Idea Design",
            content = "Create a mobile app UI Kit that provide a basic notes functionality but with some improvement. \n" +
                    "\n" +
                    "There will be a choice to select what kind of notes that user needed, so the experience while taking notes can be unique based on the needs.",
            color = NoteCardColor.Error,
            onClick = { }
        )
    }
}