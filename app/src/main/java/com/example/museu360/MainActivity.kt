package com.example.museu360

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import com.example.museu360.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding

    private val HIDE_NAVIGATION_DELAY = 300L
    private var shouldHideNavigation = true
    private var lastTouchTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val webView: WebView = findViewById(R.id.webView)

        webView.clearCache(true)
        webView.clearHistory()
        webView.clearFormData()

        val settings: WebSettings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true

        // Configura o WebViewClient personalizado para controlar o redirecionamento
        webView.webViewClient = CustomWebViewClient()

        // Carrega a página inicial do seu site
        webView.loadUrl("https://www.museusdepernambuco360.com.br/home")

        // Configuração de visualização da barra de navegação
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        // Configuração do listener de toque para mostrar/ocultar a barra de navegação
        window.decorView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastTouchTime > HIDE_NAVIGATION_DELAY) {
                    shouldHideNavigation = true
                }
                lastTouchTime = currentTime
            } else if (event.action == MotionEvent.ACTION_MOVE) {
                shouldHideNavigation = false
            }
            false
        }
    }

    private class CustomWebViewClient : WebViewClient() {
        private var redirectCount = 0
        private val MAX_REDIRECT_COUNT = 1 // Limite máximo de redirecionamentos

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val url = request?.url?.toString()

            if (url != null && url.startsWith("https://www.museusdepernambuco360.com.br/")) {
                if (redirectCount < MAX_REDIRECT_COUNT) {
                    redirectCount++
                    return false // Permite o carregamento da URL
                } else {
                    // Limite máximo de redirecionamentos atingido, carrega a página específica
                    view?.loadUrl("https://www.museusdepernambuco360.com.br/home-mobile")
                    return true // Bloqueia o redirecionamento padrão
                }
            }

            return false // Permite o carregamento da URL para outros casos
        }
    }
}
