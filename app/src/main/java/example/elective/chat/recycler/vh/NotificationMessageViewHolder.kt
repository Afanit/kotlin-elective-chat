package example.elective.chat.recycler.vh

import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.buildSpannedString
import example.elective.chat.R
import example.elective.chat.recycler.model.NotificationMessage

class NotificationMessageViewHolder(parent: ViewGroup) : MessageViewHolder(parent, R.layout.chat_item_notification) {
    private val text = itemView.findViewById<TextView>(R.id.text)

    fun bind(item: NotificationMessage) {
        val messageRes = if (item.isConnection) {
            R.string.chat_notification_join
        } else {
            R.string.chat_notification_leave
        }
        val message = text.context.getString(messageRes)

        text.text = buildSpannedString {
            append(item.sender)
            append("\n")
            append(message)
        }
    }

}
