package example.elective

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import example.elective.chat.ChatFragment
import example.elective.chat.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ChatFragment())
                .commit()
        }
    }
}