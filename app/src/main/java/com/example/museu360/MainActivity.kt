package com.example.museu360

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import com.example.museu360.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding

    private val HIDE_NAVIGATION_DELAY = 300L
    private var shouldHideNavigation = true
    private var lastTouchTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val webView: WebView = findViewById(R.id.webView)
        webView.clearCache(true)
        webView.clearHistory()
        webView.clearFormData()
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://www.museusdepernambuco360.com.br")



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
}

