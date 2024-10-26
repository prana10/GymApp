package com.example.gymapp.app.presentation.screen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LogoutDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Logout") },
        text = { Text("Anda ingin logout?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Ya")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Tidak")
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LogoutDialogPreview() {
    LogoutDialog(onDismiss = {}, onConfirm = {})
}