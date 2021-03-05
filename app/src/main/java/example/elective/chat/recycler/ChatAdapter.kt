package example.elective.chat.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import example.elective.chat.recycler.model.ChatMessage
import example.elective.chat.recycler.model.NotificationMessage
import example.elective.chat.recycler.model.TextMessage
import example.elective.chat.recycler.vh.MessageViewHolder
import example.elective.chat.recycler.vh.NotificationMessageViewHolder
import example.elective.chat.recycler.vh.TextMessageViewHolder

class ChatAdapter : ListAdapter<ChatMessage, MessageViewHolder>(ChatDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return when (viewType) {
            TYPE_TEXT -> TextMessageViewHolder(parent)
            TYPE_NOTIFICATION -> NotificationMessageViewHolder(parent)
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is TextMessageViewHolder -> holder.bind(item as TextMessage)
            is NotificationMessageViewHolder -> holder.bind(item as NotificationMessage)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TextMessage -> TYPE_TEXT
            is NotificationMessage -> TYPE_NOTIFICATION
        }
    }

    companion object {
        const val TYPE_TEXT = 1
        const val TYPE_NOTIFICATION = 2
    }
}













