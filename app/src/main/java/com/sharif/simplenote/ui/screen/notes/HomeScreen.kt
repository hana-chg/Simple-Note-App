package com.sharif.simplenote.ui.screen.notes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sharif.simplenote.R
import com.sharif.simplenote.data.models.Note
import com.sharif.simplenote.ui.components.IconMenuType
import com.sharif.simplenote.ui.components.LinkSize
import com.sharif.simplenote.ui.components.LinkType
import com.sharif.simplenote.ui.components.SearchBar
import com.sharif.simplenote.ui.components.SectionTitle
import com.sharif.simplenote.ui.components.TabBar
import com.sharif.simplenote.ui.theme.AppTypography
import com.sharif.simplenote.ui.theme.NeutralBlack
import com.sharif.simplenote.ui.theme.NeutralDarkGrey
import com.sharif.simplenote.ui.theme.PrimaryBackground


@Composable
fun HomeScreen(
    navController: NavController? = null
) {
    var selectedTab by remember { mutableStateOf(IconMenuType.Home) }
    var searchQuery by remember { mutableStateOf("") }

    val notes = remember { emptyList<Note>() }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBackground),
        bottomBar = {
            TabBar(
                selectedTab = selectedTab,
                onTabSelected = { tab -> selectedTab = tab },
                onCenterButtonClick = {
                    // Navigate to create note screen
                    navController?.navigate("create_note")
                }
            )
        },
        containerColor = Color.Transparent
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (notes.isEmpty()) {
                // Empty state
                EmptyState()
            } else {
                // Normal state
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment =Alignment.CenterHorizontally
                ) {
                    // Search bar
                    Spacer(modifier = Modifier.height(16.dp))

                    SearchBar(
                        icon = ImageVector.vectorResource(R.drawable.search_outlined),
                        searchQuery = searchQuery,
                        onSearchQueryChange = { searchQuery = it },
                        onIconClick = {

                        },
                        onSearch = { query ->
                            // Handle search action
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    SectionTitle(
                        label = stringResource(R.string.notes),
                        onLinkClick = { },
                        linkSize = LinkSize.Small,
                        linkType = LinkType.Underline
                    )

                    // Notes list
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {


                        items(notes) { note ->
                            //NoteCard(note = note)
                        }

                        item {
                            Spacer(modifier = Modifier.height(80.dp))
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Illustration
        Image(
            painter = painterResource(R.drawable.home),
            contentDescription = stringResource(R.string.home_empty_title),
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Text & Description
        Column(
            Modifier.width(237.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = stringResource(R.string.home_empty_title),
                style = AppTypography.textXiBold,
                textAlign = TextAlign.Center,
                color = NeutralBlack
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Description
            Text(
                text = stringResource(R.string.home_empty_description),
                style = AppTypography.textSmRegular,
                textAlign = TextAlign.Center,
                color = NeutralDarkGrey
            )
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}