package com.fishfromsandiego.whattodo.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.fishfromsandiego.whattodo.R
import com.fishfromsandiego.whattodo.presentation.ui.theme.WhatToDoTheme

@Composable
fun ChoreEditScreen(modifier: Modifier = Modifier) {
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var titleInputWrong by rememberSaveable { mutableStateOf(false) }
    var focusNotTriggeredYet by rememberSaveable { mutableStateOf(true) }

    val focusManager = LocalFocusManager.current

    Column(modifier = modifier) {
        TitleTextField(
            value = title,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged(
                    {
                        if (focusNotTriggeredYet) {
                            focusNotTriggeredYet = false
                            return@onFocusChanged
                        }
                        if (!it.hasFocus) {
                            if (title.isEmpty()) titleInputWrong = true
                            else {
                                titleInputWrong = false
                            }
                        }
                    }
                ),
            titleInputWrong = titleInputWrong,
            onValueChange = {
                title = it
                if (title.isEmpty()) titleInputWrong = true
                else {
                    titleInputWrong = false
                }
            },
            onKeyboardNext = {
                if (title.isEmpty()) titleInputWrong = true
                else {
                    titleInputWrong = false
                    focusManager.moveFocus(FocusDirection.Down)
                }
            }
        )
        DescriptionTextField(
            value = description,
            modifier = Modifier.fillMaxSize(),
            onValueChange = { description = it },
            onKeyboardDone = {
                focusManager.clearFocus()
            },
        )
    }
}

@Composable
fun TitleTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    onKeyboardNext: () -> Unit = {},
    titleInputWrong: Boolean = false
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = if (!titleInputWrong) stringResource(R.string.chore_edit_title)
                else stringResource(R.string.chore_edit_title_on_wrong),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        textStyle = MaterialTheme.typography.titleLarge,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next,
        ),
        keyboardActions = KeyboardActions(
            onNext = { onKeyboardNext() },
        ),
        isError = titleInputWrong,
    )
}

@Composable
fun DescriptionTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    onKeyboardDone: () -> Unit = {},
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = stringResource(R.string.chore_edit_description),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        textStyle = MaterialTheme.typography.titleLarge,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = { onKeyboardDone() }
        ),
    )
}

@Preview
@Composable
fun ChoreEditScreenPreview() {
    WhatToDoTheme(darkTheme = false, dynamicColor = false) {
        Surface {
            ChoreEditScreen(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun ChoreEditScreenPreviewDark(modifier: Modifier = Modifier) {

}