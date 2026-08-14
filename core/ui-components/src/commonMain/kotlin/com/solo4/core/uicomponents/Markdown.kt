package com.solo4.core.uicomponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mikepenz.markdown.m3.Markdown

@Composable
fun MarkdownText(
    markdown: String,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    Markdown(
        content = markdown,
        modifier = modifier.verticalScroll(scrollState),
    )
}

@Composable
fun MarkdownEditor(
    value: String,
    isPreviewEnabled: Boolean,
    onPreviewEnabledChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    placeholder: String = "Description",
) {
    Column(modifier = modifier) {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = { onPreviewEnabledChange(!isPreviewEnabled) }
            ) {
                Icon(
                    imageVector = if (isPreviewEnabled) {
                        Icons.Filled.VisibilityOff
                    } else {
                        Icons.Filled.Visibility
                    },
                    contentDescription = if (isPreviewEnabled) {
                        "Show markdown source"
                    } else {
                        "Preview markdown"
                    }
                )
            }
        }
        if (isPreviewEnabled) {
            MarkdownText(
                markdown = value,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            TextField(
                modifier = Modifier.fillMaxSize(),
                value = value,
                isError = isError,
                placeholder = { Text(text = placeholder) },
                onValueChange = onValueChange
            )
        }
    }
}
