package com.sharif.simplenote.ui.screen.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sharif.simplenote.data.models.Note
import com.sharif.simplenote.ui.components.NavBar
import com.sharif.simplenote.ui.components.StatusBar
import com.sharif.simplenote.ui.components.TaskBar
import com.sharif.simplenote.ui.components.TitleInput
import com.sharif.simplenote.ui.theme.AppTypography
import com.sharif.simplenote.ui.theme.NeutralDarkGrey
import com.sharif.simplenote.ui.theme.NeutralWhite
import com.sharif.simplenote.ui.theme.PrimaryBackground

@Composable
fun NoteEditScreen(
    noteId: String?,
    onBackClick: () -> Unit
) {
    val existingNote = remember {
        // Fetch note from database - for demo we'll use a sample
        Note(
            id = noteId,
            title = "Meeting Notes",
            description = "Discussed project timeline and deliverables.\n\n- Finalize design by Friday\n- Develop prototype next week\n- User testing in two weeks",
            createdAt = "19:30",
            updatedAt = "15:04",
            creatorName = "Ali",
            creatorUsername = "saman"
        )
    }

    var title by remember { mutableStateOf(existingNote?.title ?: "") }
    var description by remember { mutableStateOf(existingNote?.description ?: "") }

    val isEditing = existingNote != null

    val statusText = if (isEditing) {
        "Last edited on ${existingNote?.updatedAt ?: "19:30"}"
    } else {
        "Created on 17:50"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NeutralWhite)
    ) {
        // Status Bar
        StatusBar()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // NavBar
            NavBar(
                modifier = Modifier,
                showTitle = false,
                onBackClick = onBackClick,
                borderBottom = true
            )

            // Note title and description
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Title field
                TitleInput(title = title)

                TextField(
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    textStyle = AppTypography.textBaseRegular.copy(color = NeutralDarkGrey),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.Transparent)
        ) {
            TaskBar(status = statusText)
        }
    }
}


@Preview
@Composable
fun NoteEditPreview() {
//    NoteEditScreen(
//        noteId = null,
//        onBackClick = {}
//    )
    NoteEditScreen(
        noteId = "1",
        onBackClick = {}
    )

}