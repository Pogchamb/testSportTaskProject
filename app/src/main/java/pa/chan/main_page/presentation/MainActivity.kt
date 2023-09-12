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
import androidx.recyclerview.widget.LinearLayoutManager
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

        viewModel.hasLink()
        webView = findViewById(R.id.webView)

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
                    _binding?.trainRecycler?.visibility = View.GONE
                    startWebView(webView, savedInstanceState, url)
                } else {
                    _binding?.ErrorField?.visibility = View.VISIBLE
                    _binding?.webView?.visibility = View.GONE
                    _binding?.trainRecycler?.visibility = View.GONE
                }
            }
        }

        binding?.trainRecycler?.layoutManager = LinearLayoutManager(this)
        viewModel.trainLiveData.observe(this) {
            binding?.trainRecycler?.adapter = MainAdapter(it)
        }



        viewModel.linkLiveData.observe(this) { url ->
            if (url.isNullOrEmpty() || checkIsEmu()) {
                _binding?.ErrorField?.visibility = View.GONE
                _binding?.webView?.visibility = View.GONE
                _binding?.trainRecycler?.visibility = View.VISIBLE
                viewModel.startStub()
            } else {
                viewModel.saveLink(url)
                _binding?.ErrorField?.visibility = View.GONE
                _binding?.webView?.visibility = View.VISIBLE
                _binding?.trainRecycler?.visibility = View.GONE
                startWebView(webView = webView, savedInstanceState, url)
            }
        }

        viewModel.errorLiveData.observe(this) { error ->
            when (error) {
                ConnectionException -> {
                    _binding?.ErrorField?.visibility = View.VISIBLE
                    _binding?.webView?.visibility = View.GONE
                    _binding?.trainRecycler?.visibility = View.GONE
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