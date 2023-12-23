import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.logging.HttpLoggingInterceptor
import kotlin.coroutines.CoroutineContext



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SocketApp() {
    var message by remember { mutableStateOf("") }
    var receivedMessage by remember { mutableStateOf("") }

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val keyboardController = LocalSoftwareKeyboardController.current

    var webSocket by remember { mutableStateOf<WebSocket?>(null) }

    DisposableEffect(context) {
        webSocket = createWebSocket(coroutineScope) {
            receivedMessage = it
        }

        onDispose {
            webSocket?.cancel()
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // UI for sending a message
        OutlinedTextField(
            value = message,
            onValueChange = {
                message = it
            },
            label = { Text("Type a message") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    sendMessage(webSocket, message)
                    message = ""
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )

        // UI for displaying received messages
        Text(
            text = "Received: $receivedMessage",
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}

fun createWebSocket(
    coroutineScope: CoroutineScope,
    onMessageReceived: (String) -> Unit
): WebSocket {

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val request = Request.Builder()
        .url("ws://192.168.0.191:5000/ws")
        .build()



    val webSocketListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
            coroutineScope.launch {
                webSocket.send(
                    """
                    {
                        "type": "register",
                        "payload": { "email": "jarnelosschaert@gmail.com" }
                    }
                    """.trimIndent()
                )
            }
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            coroutineScope.launch {
                onMessageReceived(text)
            }
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
            super.onFailure(webSocket, t, response)
        }
    }

    return okHttpClient.newWebSocket(request, webSocketListener)
}

fun sendMessage(webSocket: WebSocket?, message: String) {
    webSocket?.send(message)
}
