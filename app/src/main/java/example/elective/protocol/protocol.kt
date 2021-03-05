package example.elective.protocol

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
sealed class ToClientEvent

@Serializable
@SerialName("Message")
data class Message(val senderId: Int, val message: String) : ToClientEvent()

@Serializable
@SerialName("Connected")
data class Connected(val id: Int) : ToClientEvent()

@Serializable
@SerialName("Disconnected")
data class Disconnected(val id: Int) : ToClientEvent()
private val json = Json.Default

fun decode(message: String): ToClientEvent {
    try {
    return json.decodeFromString(ToClientEvent.serializer(), message)
    } catch (e: Exception) {
        val a = 1
    }
    return Message(1, "")
}

fun encode(message: ToClientEvent): String {
    return json.encodeToString(ToClientEvent.serializer(), message)
}
