package example.elective.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import example.elective.protocol.ToClientEvent
import okhttp3.*

class ChatViewModel : ViewModel() {

    private val events = mutableListOf<ToClientEvent>()
    private val _messages = MutableLiveData<List<ToClientEvent>>()
    val messages: LiveData<List<ToClientEvent>> = _messages

    private val client = OkHttpClient()
    private var websocket: WebSocket? = null


    fun start(port: String, host: String) {
        websocket?.close(OK_CODE, "Normal closure")

        val listener = ChatMessageListener { event ->
            events.add(event)
            _messages.postValue(events)
        }
        val request = Request.Builder().url("ws://$host:$port").build()

        websocket = client.newWebSocket(request, listener)
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