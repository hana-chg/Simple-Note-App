package com.sharif.simplenote.ui.screen.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sharif.simplenote.ui.components.NavBar
import com.sharif.simplenote.ui.components.StatusBar
import com.sharif.simplenote.ui.components.TaskBar
import com.sharif.simplenote.ui.components.TitleInput
import com.sharif.simplenote.ui.navigation.NavItem
import com.sharif.simplenote.ui.theme.AppTypography
import com.sharif.simplenote.ui.theme.NeutralDarkGrey
import com.sharif.simplenote.ui.theme.NeutralWhite
import com.sharif.simplenote.viewModel.NotesViewModel

@Composable
fun NoteEditScreen(
    navController: NavHostController,
    noteId: Int,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val noteState by viewModel.noteState.collectAsState()
    val createState by viewModel.createState.collectAsState()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val isNewNote = noteId == 0


    // Load note if editing
    LaunchedEffect(noteId) {
        if (!isNewNote) {
            viewModel.getNote(noteId)
        }
    }

    // Update fields when note is loaded
    LaunchedEffect(noteState.note) {
        if (!isNewNote && noteState.note != null) {
            title = noteState.note!!.title
            description = noteState.note!!.description
            description = noteState.note!!.description
        }
    }

    // Navigate back on success
    LaunchedEffect(noteState.isSuccess, createState.isSuccess) {
        if (noteState.isSuccess || createState.isSuccess) {
            navController.navigate(NavItem.Home.route) {
                popUpTo(NavItem.NoteEdit.route) { inclusive = true }
            }
        }
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
                onBackClick = {
                    if (isNewNote) {
                        viewModel.createNote(title, description)
                    } else {
                        viewModel.updateNote(noteId, title, description)
                    }
                },
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
            TaskBar(status = "")
        }
    }

}

