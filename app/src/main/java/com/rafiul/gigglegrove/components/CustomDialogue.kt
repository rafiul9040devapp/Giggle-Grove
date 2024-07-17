package com.rafiul.gigglegrove.components


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties

@Composable
fun CustomDialogue(
    title:String,
    description:String,
    confirmText:String = "OK",
    dismissText:String = "Cancel",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(dismissText)
            }
        },
        title = { Text(text = title) },
        text = { Text(text = description) },
        properties = DialogProperties(dismissOnClickOutside = true)
    )
}