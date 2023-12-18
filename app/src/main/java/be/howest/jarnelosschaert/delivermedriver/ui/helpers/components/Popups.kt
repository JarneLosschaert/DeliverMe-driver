package be.howest.jarnelosschaert.deliverme.ui.helpers.components

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
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
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit = {}
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
                    onConfirm(content)
                    onDismiss()
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
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit = {}
) {
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
                    onConfirm(text)
                    onDismiss()
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
fun Loader(modifier: Modifier = Modifier, Text: String, withCancel : Boolean = false, onCancel: () -> Unit = {}) {
    Column(
        modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text(
            text = Text,
            modifier = Modifier.padding(top = 20.dp),
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h6
        )
        if (withCancel) {
            TextButton(
                onClick = onCancel,
                modifier = Modifier.padding(top = 10.dp),
            ) {
                Text(
                    text = "Cancel",
                    color = MaterialTheme.colors.onBackground,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}
