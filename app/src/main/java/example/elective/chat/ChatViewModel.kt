package example.elective.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import example.elective.protocol.ToClientEvent
import okhttp3.*

class ChatViewModel : ViewModel() {

    private val eventData = mutableListOf<ToClientEvent>()
    private val _events = MutableLiveData<List<ToClientEvent>>()
    val events: LiveData<List<ToClientEvent>> = _events

    private val client = OkHttpClient()
    private var websocket: WebSocket? = null

    fun start(host: String = "192.168.1.66", port: String = "8885") {
        websocket?.close(OK_CODE, "Normal closure")

        val listener = ChatMessageListener { event ->
            eventData.add(event)
            _events.postValue(eventData)
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