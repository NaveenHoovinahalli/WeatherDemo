package com.example.weatherdemo.ui.component

import androidx.compose.material.AlertDialog

import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun TwoButtonAlert(
    onOkayClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            // Handle dismiss if necessary
        },
        title = {
            Text(text = "Location Permission Needed")
        },
        text = {
            Text(text = "This app requires location permission to function correctly. Please grant the permission.")
        },
        confirmButton = {
            TextButton(onClick = onOkayClick) {
                Text(text = "Okay")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelClick) {
                Text(text = "Cancel")
            }
        }
    )
}

@Composable
fun LocationPermissionDialog(onOkayClick: () -> Unit, onCancelClick: () -> Unit) {
    TwoButtonAlert(onOkayClick = { onOkayClick() }, onCancelClick = {
        onCancelClick()
    })
}