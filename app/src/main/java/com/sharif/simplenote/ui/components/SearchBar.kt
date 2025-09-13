package com.sharif.simplenote.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sharif.simplenote.R
import com.sharif.simplenote.ui.theme.AppTypography
import com.sharif.simplenote.ui.theme.NeutralBaseGrey
import com.sharif.simplenote.ui.theme.NeutralLightGrey

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = stringResource(R.string.search_placeholder),
    onSearch: (String) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .padding(horizontal = 9.dp, vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Back icon
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = stringResource(R.string.back),
            )
        }

        // Search field
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 7.dp, vertical = 7.dp),
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            label = {
                Text(
                    text = placeholderText,
                    style = AppTypography.textSmRegular,
                    color = NeutralBaseGrey,
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            onSearchQueryChange("")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.clear_search),
                        )
                    }
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(searchQuery)
                }
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = NeutralLightGrey,
                unfocusedContainerColor = NeutralLightGrey,
                disabledContainerColor = NeutralLightGrey,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
            ),
            shape = RoundedCornerShape(8.dp),
            textStyle = AppTypography.textSmRegular
        )
    }
}


@Preview
@Composable
fun SearchPreview() {

    SearchBar(
        searchQuery = "",
        onSearchQueryChange = {},
        onBackClick = {},
        modifier = Modifier,
        onSearch = { TODO() }
    )

}
