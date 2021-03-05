package example.elective.chat.recycler.vh

import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.italic
import example.elective.chat.R
import example.elective.chat.recycler.model.TextMessage

class TextMessageViewHolder(parent: ViewGroup) : MessageViewHolder(parent, R.layout.chat_item_text) {
    private val text = itemView.findViewById<TextView>(R.id.text)

    fun bind(item: TextMessage) {
        if (item.fromCurrentUser) {
            text.setBackgroundColor(ContextCompat.getColor(text.context, R.color.yellow))
            text.text = item.message

            val params = (text.layoutParams as FrameLayout.LayoutParams).apply {
                gravity = Gravity.END
            }
            text.layoutParams = params
        } else {
            text.setBackgroundColor(ContextCompat.getColor(text.context, R.color.orange))
            text.text = buildSpannedString {
                append(item.sender)
                italic {
                    append(": ")
                }
                append(item.message)
            }

            val params = (text.layoutParams as FrameLayout.LayoutParams).apply {
                gravity = Gravity.START
            }
            text.layoutParams = params
        }

    }
}