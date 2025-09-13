package com.sharif.simplenote.ui.screen.notes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.sharif.simplenote.R
import com.sharif.simplenote.ui.components.IconMenuType
import com.sharif.simplenote.ui.components.LinkSize
import com.sharif.simplenote.ui.components.LinkType
import com.sharif.simplenote.ui.components.NoteCard
import com.sharif.simplenote.ui.components.SearchBar
import com.sharif.simplenote.ui.components.SectionTitle
import com.sharif.simplenote.ui.components.StatusBar
import com.sharif.simplenote.ui.components.TabBar
import com.sharif.simplenote.ui.theme.AppTypography
import com.sharif.simplenote.ui.theme.NeutralBlack
import com.sharif.simplenote.ui.theme.NeutralDarkGrey
import com.sharif.simplenote.ui.theme.PrimaryBackground
import com.sharif.simplenote.viewModel.NotesViewModel


@Composable
fun HomeScreen(
    navController: NavHostController, viewModel: NotesViewModel
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val notes = viewModel.notesFlow.collectAsLazyPagingItems()
    var selectedTab by remember { mutableStateOf(IconMenuType.Home) }




    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBackground)
    ) {

        StatusBar()

        // Main content
        if (notes.itemCount == 0) {
            EmptyState(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 84.dp) // Padding to avoid TabBar overlap
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 84.dp), // Padding to avoid TabBar overlap
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                SearchBar(
                    icon = ImageVector.vectorResource(R.drawable.search_outlined),
                    searchQuery = searchQuery,
                    onSearchQueryChange = { viewModel.updateSearchQuery(it) },
                    onIconClick = {},
                    onSearch = { query -> }
                )

                Spacer(modifier = Modifier.height(24.dp))

                SectionTitle(
                    label = stringResource(R.string.notes),
                    onLinkClick = { },
                    linkSize = LinkSize.Small,
                    linkType = LinkType.Underline
                )


                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(notes.itemCount) { index ->
                        val note = notes[index]
                        if (note != null) {
                            NoteCard(
                                title = note.title,
                                content = note.description,
                                onClick = { navController.navigate("note/edit/${note.id}") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

        }
    }

    // TabBar positioned absolutely at the bottom
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        TabBar(
            modifier = Modifier.align(Alignment.BottomEnd),  // Move align inside the Box content
            selectedTab = selectedTab,
            onTabSelected = { tab -> selectedTab = tab },
            onCenterButtonClick = {
                navController.navigate("note_edit/0")
            }
        )
    }
}



@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
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
            Modifier.width(237.dp), horizontalAlignment = Alignment.CenterHorizontally
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

