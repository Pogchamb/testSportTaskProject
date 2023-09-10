package pa.chan.main_page.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pa.chan.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        // TODO
        super.onSaveInstanceState(outState)
    }
}