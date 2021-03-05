package example.elective.chat.recycler.model

sealed class ChatMessage(val sender: String)

class NotificationMessage(sender: String, val isConnection: Boolean) : ChatMessage(sender)

class TextMessage(sender: String, val message: String, val fromCurrentUser: Boolean) : ChatMessage(sender)