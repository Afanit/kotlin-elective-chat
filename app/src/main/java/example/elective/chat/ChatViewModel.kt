package example.elective.chat

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import example.elective.chat.recycler.model.ChatMessage
import example.elective.chat.recycler.model.NotificationMessage
import example.elective.chat.recycler.model.TextMessage
import example.elective.protocol.ToClientEvent
import example.elective.protocol.Connected
import example.elective.protocol.Disconnected
import example.elective.protocol.Message
import okhttp3.*

class ChatViewModel : ViewModel() {

    private val convertedEvents = mutableListOf<ChatMessage>()
    private val _messages = MutableLiveData<List<ChatMessage>>()
    val messages: LiveData<List<ChatMessage>> = _messages

    private lateinit var emojiMapper: EmojiMapper
    private var userId: Int = -1

    private val client = OkHttpClient()
    private var websocket: WebSocket? = null

    fun init(context: Context) {
        this.emojiMapper = EmojiMapper(context)
    }

    fun start(host: String = "192.168.1.66", port: String = "8885") {
        websocket?.close(OK_CODE, "Normal closure")

        val listener = ChatMessageListener { event ->
            convertedEvents.add(transform(event))
            _messages.postValue(convertedEvents)
        }
        val request = Request.Builder().url("ws://$host:$port").build()

        websocket = client.newWebSocket(request, listener)
    }

    private fun transform(event: ToClientEvent): ChatMessage {
        return when (event) {
            is Message -> TextMessage(emojiMapper.map(event.senderId), event.message, event.senderId == userId)
            is Connected -> {
                if (userId == -1) {
                    userId = event.id
                }
                NotificationMessage(emojiMapper.map(event.id), true)
            }
            is Disconnected -> {
                NotificationMessage(emojiMapper.map(event.id), false)
            }
        }
    }

    fun send(message: String) {
        websocket?.send(message)
    }

    override fun onCleared() {
        websocket?.close(OK_CODE, "Normal closure")
        client.dispatcher.executorService.shutdown()
    }

    companion object {
        private const val OK_CODE = 1000
    }
}