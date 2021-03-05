package example.elective.chat

import example.elective.*
import example.elective.protocol.ToClientEvent
import example.elective.protocol.decode
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class ChatMessageListener(val onNewMessage: (ToClientEvent) -> Unit): WebSocketListener() {
    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)

        onNewMessage(decode(text))
    }
}