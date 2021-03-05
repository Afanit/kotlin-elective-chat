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
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import example.elective.chat.recycler.ChatAdapter
import java.lang.IllegalStateException

class ChatFragment : Fragment() {

    private lateinit var viewModel: ChatViewModel

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

        val host = arguments?.getString(HOST_KEY) ?: throw IllegalStateException("Host argument must be")
        val port = arguments?.getString(PORT_KEY) ?: throw IllegalStateException("Port argument must be")

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

        viewModel.start(host, port)
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