package example.elective.chat

import android.content.Context

class EmojiMapper(context: Context) {

    private val emoji = context.resources.getStringArray(R.array.emoji).apply {
        shuffle()
    }

    fun map(id: Int): String {
        return emoji[id % emoji.size]
    }
}