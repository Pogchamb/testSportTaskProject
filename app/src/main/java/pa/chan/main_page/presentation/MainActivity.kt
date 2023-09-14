package pa.chan.main_page.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import pa.chan.BuildConfig
import pa.chan.R
import pa.chan.databinding.ActivityMainBinding
import pa.chan.main_page.data.userException.ConnectionException
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
        viewModel.hasLinkLiveData.observe(this) { url ->
            if (url.isNullOrEmpty()) {
                viewModel.getUrl()
            } else {
                val connectionManager: ConnectivityManager =
                    this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
                val isConnected = activeNetwork?.isConnectedOrConnecting == true

                if (isConnected) {
                    _binding?.ErrorField?.visibility = View.GONE
                    _binding?.webView?.visibility = View.VISIBLE
                    _binding?.quizLayout?.visibility = View.GONE
                    binding?.startLayout?.visibility = View.GONE
                    startWebView(webView, savedInstanceState, url)
                } else {
                    _binding?.ErrorField?.visibility = View.VISIBLE
                    _binding?.webView?.visibility = View.GONE
                    _binding?.quizLayout?.visibility = View.GONE
                    binding?.startLayout?.visibility = View.GONE
                }
            }
        }

        viewModel.quizLiveData.observe(this) {
            binding?.startLayout?.visibility = View.VISIBLE
        }

        binding?.nextAnswer?.setOnClickListener {
            binding?.startGame(totalScore)
            viewModel.nextQuestion()
        }

        binding?.newGame?.setOnClickListener {
            totalScore = 0
            binding?.refreshGame(totalScore, this)
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


        viewModel.linkLiveData.observe(this) { url ->
            if (checkIsEmu() || url.isNullOrEmpty()) {
                _binding?.ErrorField?.visibility = View.GONE
                _binding?.webView?.visibility = View.GONE
                _binding?.quizLayout?.visibility = View.GONE
                viewModel.startStub(this)
            } else {
                viewModel.saveLink(url)
                _binding?.ErrorField?.visibility = View.GONE
                _binding?.webView?.visibility = View.VISIBLE
                _binding?.quizLayout?.visibility = View.GONE
                binding?.startLayout?.visibility = View.GONE
                startWebView(webView = webView, savedInstanceState, url)
            }
        }

        viewModel.errorLiveData.observe(this) { error ->
            when (error) {
                ConnectionException -> {
                    _binding?.ErrorField?.visibility = View.VISIBLE
                    _binding?.webView?.visibility = View.GONE
                    _binding?.quizLayout?.visibility = View.GONE
                    binding?.startLayout?.visibility = View.GONE
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        binding?.webView?.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        }
    }
}

private fun ActivityMainBinding.refreshGame(score: Int, context: Context) {
    this.startLayout.visibility = View.VISIBLE
    this.ErrorField.visibility = View.GONE
    this.webView.visibility = View.GONE
    this.score.text = score.toString()
    this.textRules.text = context.getString(R.string.rules)
    this.nextAnswer.text = context.getString(R.string.start)
    this.quizLayout.visibility = View.GONE
}

private fun ActivityMainBinding.startGame(score: Int) {
    this.startLayout.visibility = View.GONE
    this.ErrorField.visibility = View.GONE
    this.webView.visibility = View.GONE
    this.score.text = score.toString()
    this.quizLayout.visibility = View.VISIBLE
}

private fun ActivityMainBinding.nextQuestion(
    rightAnswer: String?,
    score: Int,
    answer: String,
    context: Context
) {
    this.startLayout.visibility = View.VISIBLE
    this.ErrorField.visibility = View.GONE
    this.webView.visibility = View.GONE
    this.quizLayout.visibility = View.GONE
    if (rightAnswer == answer) {
        this.textRules.text =
            String.format(context.getString(R.string.rightAnswerStr), rightAnswer, answer)
    } else {
        this.textRules.text =
            String.format(context.getString(R.string.rightAnswerStr), rightAnswer, answer)
    }

    this.score.text = score.toString()
    this.nextAnswer.text = "Next"
}

private fun startWebView(webView: WebView, savedInstanceState: Bundle?, url: String) {
    webView.webViewClient = WebViewClient()
    val webSettings = webView.settings
    webSettings.javaScriptEnabled = true
    if (savedInstanceState != null)
        webView.restoreState(savedInstanceState)
    else
        webView.loadUrl(url)
    webView.settings.domStorageEnabled = true
    webView.settings.javaScriptCanOpenWindowsAutomatically = true
    val cookieManager = CookieManager.getInstance()
    cookieManager.setAcceptCookie(true)
    val mWebSettings = webView.settings
    mWebSettings.javaScriptEnabled = true
    mWebSettings.loadWithOverviewMode = true
    mWebSettings.useWideViewPort = true
    mWebSettings.domStorageEnabled = true
    mWebSettings.databaseEnabled = true
    mWebSettings.setSupportZoom(false)
    mWebSettings.allowFileAccess = true
    mWebSettings.allowContentAccess = true
    mWebSettings.loadWithOverviewMode = true
    mWebSettings.useWideViewPort = true
}

private fun checkIsEmu(): Boolean {
    if (BuildConfig.DEBUG) return false // when developer use this build on emulator
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