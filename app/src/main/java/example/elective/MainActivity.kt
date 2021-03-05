package example.elective

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import example.elective.chat.ChatFragment
import example.elective.chat.R
import example.elective.welcome.WelcomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WelcomeFragment.newInstance())
                .commit()
        }
    }

    fun goChat(host: String, port: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ChatFragment.newInstance(host, port))
            .addToBackStack("ChatFragment")
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }
}