package appcalendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import appcalendar.model.AppCalendarItemModel

@Composable
fun AppCalendarBlock(
    modifier: Modifier = Modifier,
    model: AppCalendarItemModel,
    onItemClicked: (AppCalendarItemModel) -> Unit
) {
    Box(
        modifier = modifier
            .clickable(
                indication = rememberRipple(bounded = false),
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onItemClicked(model) }
            )
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            maxLines = 1,
            text = model.day.toString()
        )
    }
}