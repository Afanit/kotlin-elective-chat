package example.elective.chat.simple_recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import example.elective.chat.R
import example.elective.protocol.Connected
import example.elective.protocol.Disconnected
import example.elective.protocol.Message
import example.elective.protocol.ToClientEvent

class SimpleChatAdapter : ListAdapter<ToClientEvent, SimpleViewHolder>(SimpleChatDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_item_simple, parent, false)
        return SimpleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SimpleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val view = view as TextView

    fun bind(event: ToClientEvent) {
        view.text = when (event) {
            is Message -> "From ${event.senderId}: ${event.message}"
            is Connected -> "User ${event.id} connected"
            is Disconnected -> "User ${event.id} disconnected"
        }
    }
}

class SimpleChatDiffCallback : DiffUtil.ItemCallback<ToClientEvent>() {
    override fun areItemsTheSame(oldItem: ToClientEvent, newItem: ToClientEvent): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ToClientEvent, newItem: ToClientEvent): Boolean {
        return oldItem == newItem
    }
}