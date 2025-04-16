//package com.fishfromsandiego.whattodo.presentation.ui
//
//import androidx.compose.foundation.gestures.awaitEachGesture
//import androidx.compose.foundation.gestures.awaitFirstDown
//import androidx.compose.foundation.gestures.waitForUpOrCancellation
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.imePadding
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.DateRange
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material3.DatePicker
//import androidx.compose.material3.DatePickerDialog
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.material3.TextField
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.material3.rememberDatePickerState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.focus.FocusDirection
//import androidx.compose.ui.focus.onFocusChanged
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.input.pointer.PointerEventPass
//import androidx.compose.ui.input.pointer.pointerInput
//import androidx.compose.ui.platform.LocalFocusManager
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.TextRange
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.tooling.preview.Preview
//import com.fishfromsandiego.whattodo.presentation.R
//import com.fishfromsandiego.whattodo.presentation.ui.theme.WhatToDoTheme
//import java.time.Instant
//import java.time.LocalDate
//import java.time.ZoneOffset
//import java.time.format.DateTimeFormatter
//
//@Composable
//fun ChoreEditScreen(modifier: Modifier = Modifier) {
//    var title by rememberSaveable { mutableStateOf("") }
//
//    var description by remember {
//        val textFieldValue = TextFieldValue(text = "", selection = TextRange.Zero)
//        mutableStateOf(textFieldValue)
//    }
//    var titleInputWrong by rememberSaveable { mutableStateOf(false) }
//    var titleFocusNotTriggeredYet by rememberSaveable { mutableStateOf(true) }
//    var selectedDateMillis by rememberSaveable { mutableStateOf<Long?>(null) }
//    var dateWrong by rememberSaveable { mutableStateOf(false) }
//    var showPicker by rememberSaveable { mutableStateOf(false) }
//
//    val focusManager = LocalFocusManager.current
//
//
//    Column(modifier = modifier) {
//        TitleTextField(
//            value = title,
//            modifier = Modifier
//                .fillMaxWidth()
//                .onFocusChanged {
//                    if (titleFocusNotTriggeredYet) {
//                        titleFocusNotTriggeredYet = false
//                        return@onFocusChanged
//                    }
//                    if (!it.hasFocus) {
//                        titleInputWrong = title.isEmpty()
//                    }
//                },
//            titleInputWrong = titleInputWrong,
//            onValueChange = {
//                title = it
//                titleInputWrong = title.isEmpty()
//            },
//            onKeyboardNext = {
//                if (title.isEmpty()) titleInputWrong = true
//                else {
//                    titleInputWrong = false
//                    focusManager.moveFocus(FocusDirection.Down)
//                }
//            }
//        )
//
//        ChoreDatePicker(
//            selectedDate = selectedDateMillis,
//            showPicker = showPicker,
//            modifier = Modifier
//                .fillMaxWidth()
//                .onFocusChanged(
//                    { if (it.hasFocus) showPicker = true }
//                ),
//            onDateSelected = {
//                selectedDateMillis = it
//                dateWrong = selectedDateMillis == null
//                if (!dateWrong)
//                    focusManager.moveFocus(FocusDirection.Down)
//            },
//            onFieldClick = { showPicker = true },
//            onDateSelectionDismiss = {
//                showPicker = false
//                dateWrong = selectedDateMillis == null
//            },
//            dateWrong = dateWrong,
//        )
//
//        DescriptionTextField(
//            value = description,
//            modifier = Modifier
//                .imePadding()
//                .fillMaxSize(),
//            onValueChange = { description = it },
//            onKeyboardDone = {
//                focusManager.clearFocus()
//            },
//        )
//    }
//}
//
//@Composable
//fun TitleTextField(
//    modifier: Modifier = Modifier,
//    value: String = "",
//    onValueChange: (String) -> Unit = {},
//    onKeyboardNext: () -> Unit = {},
//    titleInputWrong: Boolean = false
//) {
//    TextField(
//        modifier = modifier,
//        value = value,
//        onValueChange = onValueChange,
//        label = {
//            Text(
//                text = if (!titleInputWrong) stringResource(R.string.chore_edit_title)
//                else stringResource(R.string.chore_edit_title_on_wrong),
//                style = MaterialTheme.typography.labelLarge,
//                fontSize = MaterialTheme.typography.titleMedium.fontSize,
//            )
//        },
//        trailingIcon = {
//            Icon(Icons.Filled.Home, contentDescription = stringResource(R.string.chore_edit_title))
//        },
//        textStyle = MaterialTheme.typography.titleLarge,
//        keyboardOptions = KeyboardOptions.Default.copy(
//            keyboardType = KeyboardType.Text,
//            imeAction = ImeAction.Next,
//        ),
//        keyboardActions = KeyboardActions(
//            onNext = { onKeyboardNext() },
//        ),
//        isError = titleInputWrong,
//    )
//}
//
//@Composable
//fun DescriptionTextField(
//    modifier: Modifier = Modifier,
//    value: TextFieldValue = TextFieldValue(""),
//    onValueChange: (TextFieldValue) -> Unit = {},
//    onKeyboardDone: () -> Unit = {},
//) {
//    TextField(
//        modifier = modifier,
//        value = value,
//        onValueChange = onValueChange,
//        label = {
//            Text(
//                text = stringResource(R.string.chore_edit_description),
//                style = MaterialTheme.typography.labelLarge,
//                fontSize = MaterialTheme.typography.titleMedium.fontSize,
//            )
//        },
//        textStyle = MaterialTheme.typography.bodyLarge,
//        keyboardOptions = KeyboardOptions.Default.copy(
//            keyboardType = KeyboardType.Text,
//            imeAction = ImeAction.None,
//        ),
//        keyboardActions = KeyboardActions(
//            onDone = { onKeyboardDone() }
//        ),
//    )
//}
//
//@Composable
//fun ChoreDatePicker(
//    selectedDate: Long?,
//    showPicker: Boolean,
//    modifier: Modifier = Modifier,
//    dateWrong: Boolean = false,
//    onFieldClick: () -> Unit = {},
//    onDateSelected: (Long?) -> Unit = {},
//    onDateSelectionDismiss: () -> Unit = {},
//) {
//    TextField(
//        value = selectedDate?.let { convertMillisToDate(it) } ?: "",
//        onValueChange = { },
//        colors = TextFieldDefaults.colors().copy(
//            cursorColor = Color.Transparent,
//            errorCursorColor = Color.Transparent,
//        ),
//        keyboardOptions = KeyboardOptions.Default.copy(
//            showKeyboardOnFocus = false,
//        ),
//        isError = dateWrong,
//        label = {
//            Text(
//                text = if (!dateWrong) stringResource(R.string.chore_edit_select_date)
//                else stringResource(R.string.chore_edit_date_wrong),
//                style = MaterialTheme.typography.labelLarge,
//                fontSize = MaterialTheme.typography.titleMedium.fontSize,
//            )
//        },
//        placeholder = { Text(stringResource(R.string.chore_date_format_pattern)) },
//        trailingIcon = {
//            Icon(
//                Icons.Filled.DateRange,
//                contentDescription = stringResource(R.string.chore_edit_select_date)
//            )
//        },
//        modifier = modifier
//            .fillMaxWidth()
//            .pointerInput(selectedDate) {
//                awaitEachGesture {
//                    // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
//                    // in the Initial pass to observe events before the text field consumes them
//                    // in the Main pass.
//                    awaitFirstDown(pass = PointerEventPass.Initial)
//                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
////                    If the gesture was not canceled, the final up change is returned
////                    or null if the event was canceled.
//                    if (upEvent != null) {
//                        onFieldClick()
//                    }
//                }
//            }
//    )
//
//    if (showPicker) {
//        DatePickerModal(
//            onDateSelected = onDateSelected,
//            onDismiss = onDateSelectionDismiss,
//        )
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DatePickerModal(
//    onDateSelected: (Long?) -> Unit,
//    onDismiss: () -> Unit
//) {
//    val datePickerState = rememberDatePickerState()
//
//    DatePickerDialog(
//        onDismissRequest = onDismiss,
//        confirmButton = {
//            TextButton(onClick = {
//                onDateSelected(datePickerState.selectedDateMillis)
//                onDismiss()
//            }) {
//                Text("OK")
//            }
//        },
//        dismissButton = {
//            TextButton(onClick = onDismiss) {
//                Text("Cancel")
//            }
//        }
//    ) {
//        DatePicker(state = datePickerState)
//    }
//}
//
//@Composable
//fun convertMillisToDate(millis: Long): String {
//    return DateTimeFormatter.ofPattern(stringResource(R.string.chore_date_format_pattern)).format(
//        Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDate()
//    )
//}
//
//@Preview
//@Composable
//fun Millis(modifier: Modifier = Modifier) {
//    Text(
//        convertMillisToDate(
//            LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
//        ).toString()
//    )
//}
//
//
//@Preview
//@Composable
//fun ChoreEditScreenPreview() {
//    WhatToDoTheme(darkTheme = false, dynamicColor = false) {
//        Surface {
//            ChoreEditScreen(modifier = Modifier.fillMaxSize())
//        }
//    }
//}
//
//@Preview
//@Composable
//fun ChoreEditScreenPreviewDark(modifier: Modifier = Modifier) {
//    WhatToDoTheme(darkTheme = true, dynamicColor = false) {
//        Surface {
//            ChoreEditScreen(modifier = Modifier.fillMaxSize())
//        }
//    }
//}