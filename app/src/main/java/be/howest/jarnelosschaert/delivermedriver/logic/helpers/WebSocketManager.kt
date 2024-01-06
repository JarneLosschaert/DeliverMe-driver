package be.howest.jarnelosschaert.delivermedriver.logic.helpers

import be.howest.jarnelosschaert.delivermedriver.logic.models.Delivery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.logging.HttpLoggingInterceptor

class WebSocketManager(
    coroutineScope: CoroutineScope,
    email: String
) {
    private val webSocket: WebSocket = createWebSocket(coroutineScope, email)

    fun sendLocationUpdate(delivery: Delivery, lat: Double, lon: Double) {
        val locationUpdateMessage = """
            {
                "type": "location",
                "payload": {
                    "lat": $lat,
                    "lon": $lon,
                    "delivery": ${toJsonString(delivery)}
                }
            }
        """.trimIndent()

        webSocket.send(locationUpdateMessage)
    }

    fun cancelWebSocket() {
        webSocket.cancel()
    }

    private fun toJsonString(delivery: Delivery): String {
        val json = Json { ignoreUnknownKeys = true }
        return json.encodeToString(Delivery.serializer(), delivery)
    }

    private fun createWebSocket(
        coroutineScope: CoroutineScope,
        email: String
    ): WebSocket {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        val request = Request.Builder()
            .url("ws:notifier.deliverme.site/ws")
            .build()

        val webSocketListener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
                coroutineScope.launch {
                    webSocket.send(
                        """
                    {
                        "type": "register",
                        "payload": { "email": "$email" }
                    }
                    """.trimIndent()
                    )
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
}
