package appcalendar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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