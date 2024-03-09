package com.solo4.calendarreminder.presentation.components.appcalendar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Notifier(modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier.size(8.dp)
    ) {
        drawCircle(
            brush = Brush.linearGradient(
                listOf(
                    Color(0xFFE94747),
                    Color(0xFFDA1414)
                )
            )
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun NotifierPreview() {
    Notifier()
}