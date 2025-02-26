package com.solo4.core.uicomponents

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Dialog(
    title: String,
    description: String,
    isVisible: Boolean,
    onConfirmClicked: () -> Unit,
    onDismissClicked: () -> Unit,
    confirmText: String = "Ok",
    dismissText: String = "Cancel"
) {
    if (isVisible) {
        AlertDialog(
            onDismissRequest = onDismissClicked,
            modifier = Modifier,
            icon = {},
            title = {
                Text(
                    text = title
                )
            },
            text = {
                Text(
                    text = description
                )
            },
            shape = AlertDialogDefaults.shape,
            confirmButton = {
                Button(
                    onClick = onConfirmClicked
                ) {
                    Text(text = confirmText)
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismissClicked
                ) {
                    Text(text = dismissText)
                }
            },
        )
    }
}