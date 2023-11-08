package be.howest.jarnelosschaert.delivermedriver.ui.helpers.components

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun GeneralPopup(
    title: String,
    content: String,
    confirmButton: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = {
            Text(text = content)
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                    onDismiss()
                }
            ) {
                Text(text = confirmButton)
            }
        }
    )
}

@Composable
fun GeneralChoicePopup(
    title: String,
    content: String,
    confirmButton: String,
    toastText: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit = {}
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = {
            Text(text = content)
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                    onDismiss()
                    Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
                }
            ) {
                Text(text = confirmButton)
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Text(text = "Cancel")
            }
        },
    )
}


@Composable
fun GeneralTextPopup(
    title: String,
    label: String,
    confirmButton: String,
    toastText: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit = {}
) {
    val context = LocalContext.current
    var text by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = {
            Column {
                Text(text = label)
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                    onDismiss()
                    Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
                }
            ) {
                Text(text = confirmButton)
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Text(text = "Cancel")
            }
        },
    )
}
