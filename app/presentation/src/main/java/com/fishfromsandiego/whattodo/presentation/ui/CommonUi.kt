package com.fishfromsandiego.whattodo.presentation.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fishfromsandiego.whattodo.presentation.ui.theme.WhatToDoTheme

@Composable
fun imagePlaceholder(@DrawableRes debugPreview: Int) =
    if (LocalInspectionMode.current) {
        painterResource(id = debugPreview)
    } else {
        null
    }

@Composable
fun ErrorIcon(errorText: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            Icons.Default.Warning,
            tint = MaterialTheme.colorScheme.error,
            contentDescription = null,
            modifier = Modifier
                .size(250.dp)
                .padding(8.dp)
        )
        Text(
            errorText,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun ImageNotAvailable(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Icon(
            Icons.Default.Cancel,
            contentDescription = null,
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
        )
        Text(
            "N\\A",
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun ProgressIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorIconPreview(modifier: Modifier = Modifier) {
    WhatToDoTheme(darkTheme = false, dynamicColor = false) {
        Surface {
            ErrorIcon(
                errorText = "Some error occured so i decided to show it on preview",
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ErrorIconPreviewDark(modifier: Modifier = Modifier) {
    WhatToDoTheme(darkTheme = true, dynamicColor = false) {
        Surface {
            ErrorIcon(
                errorText = "Some error occured so i decided to show it on preview",
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}