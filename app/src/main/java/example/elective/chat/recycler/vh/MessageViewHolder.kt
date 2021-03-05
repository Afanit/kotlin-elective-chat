package example.elective.chat.recycler.vh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

open class MessageViewHolder(
    parent: ViewGroup,
    @LayoutRes layoutRes: Int
) : RecyclerView.ViewHolder(parent.inflate(layoutRes, attachToRoot = false))

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
