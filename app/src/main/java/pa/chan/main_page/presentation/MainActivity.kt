package pa.chan.main_page.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pa.chan.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        viewModel.startStub()

        binding?.trainRecycler?.layoutManager = LinearLayoutManager(this)

        viewModel.trainLiveData.observe(this) {
            binding?.trainRecycler?.adapter = MainAdapter(it)
        }


    }


    override fun onSaveInstanceState(outState: Bundle) {
        // TODO
        super.onSaveInstanceState(outState)
    }
}