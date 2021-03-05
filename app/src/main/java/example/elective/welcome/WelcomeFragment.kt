package example.elective.welcome

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import example.elective.MainActivity
import example.elective.chat.R

class WelcomeFragment : Fragment() {

    companion object {
        fun newInstance() = WelcomeFragment()
    }

    private lateinit var viewModel: WelcomeViewModel

    private lateinit var host: EditText
    private lateinit var port: EditText
    private lateinit var start: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.welcome_fragment, container, false).apply {
            host = findViewById(R.id.host)
            port = findViewById(R.id.port)
            start = findViewById(R.id.start)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(WelcomeViewModel::class.java)

        start.setOnClickListener {
            val host = host.text.toString()
            val port = port.text.toString()

            (requireActivity() as MainActivity).goChat(host, port)
        }
    }
}