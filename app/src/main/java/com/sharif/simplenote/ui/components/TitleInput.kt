package com.sharif.simplenote.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sharif.simplenote.ui.theme.AppTypography
import com.sharif.simplenote.ui.theme.NeutralBaseGrey
import com.sharif.simplenote.ui.theme.NeutralBlack

enum class TitleInputState {
    Default, Filled
}

@Composable
fun TitleInput(
    state: TitleInputState = TitleInputState.Filled,
    title: String = "",
    onTitleChange: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var textValue by remember { mutableStateOf(title) }
    val color = if (state == TitleInputState.Default) NeutralBaseGrey else NeutralBlack

    TextField(
        value = textValue,
        onValueChange = {
            textValue = it
            onTitleChange(it)
        },
        textStyle = AppTypography.text2xiBold.copy(color = color),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        modifier = modifier
            .padding(bottom = 4.dp)
    )
}

@Preview
@Composable
fun PreviewTitleInput() {
    Column {
        TitleInput(
            state = TitleInputState.Default,
            title = "Default Title Input"
        )
        Spacer(modifier = Modifier.height(16.dp))
        TitleInput(
            state = TitleInputState.Filled,
            title = "Filled Title Input"
        )
    }
}