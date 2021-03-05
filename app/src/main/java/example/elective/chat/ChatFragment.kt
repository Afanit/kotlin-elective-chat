package example.elective.chat

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import example.elective.chat.recycler.ChatAdapter
import example.elective.protocol.Connected
import example.elective.protocol.Disconnected
import example.elective.protocol.Message
import example.elective.protocol.ToClientEvent
import java.lang.IllegalStateException

class ChatFragment : Fragment() {

    private lateinit var viewModel: ChatViewModel

//    private lateinit var list: LinearLayout
    private lateinit var messages: RecyclerView
    private lateinit var adapter: ChatAdapter

    private lateinit var input: EditText
    private lateinit var send: Button

    override fun onAttach(context: Context) {
        super.onAttach(context)

        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.chat_fragment_advanced, container, false).apply {
            messages = findViewById(R.id.messages)
            input = findViewById(R.id.input)
            send = findViewById(R.id.send)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val port = arguments?.getString(PORT_KEY) ?: throw IllegalStateException("Port argument must be")
        val host = arguments?.getString(HOST_KEY) ?: throw IllegalStateException("Host argument must be")

//        viewModel.events.observe(viewLifecycleOwner) { events ->
//            list.removeAllViews()
//
//            events.forEach { event ->
//                list.addView(buildMessageView(list, event))
//            }
//        }

        adapter = ChatAdapter()
        messages.adapter = adapter

        viewModel.init(requireContext())

        viewModel.messages.observe(viewLifecycleOwner) { events ->
            adapter.submitList(events)
            messages.scrollToPosition(events.size - 1)
        }

        send.setOnClickListener {
            viewModel.send(input.text.toString())
            input.setText("")
        }

        input.addTextChangedListener {
            send.isEnabled = !it.isNullOrEmpty()
        }

        viewModel.start(port, host)
    }

    private fun buildMessageView(parent: ViewGroup): TextView {
        return LayoutInflater.from(requireContext())
            .inflate(R.layout.chat_item_text, parent, false) as TextView
    }

    private fun bindMessageView(view: TextView, event: ToClientEvent) {
        view.text = when (event) {
            is Message -> "From ${event.senderId}: ${event.message}"
            is Connected -> "User ${event.id} connected"
            is Disconnected -> "User ${event.id} disconnected"
        }
    }

    companion object {
        private const val HOST_KEY = "host"
        private const val PORT_KEY = "port"

        fun newInstance(host: String, port: String): Fragment {
            return ChatFragment().apply {
                arguments = bundleOf(
                    PORT_KEY to port,
                    HOST_KEY to host
                )
            }
        }
    }
}