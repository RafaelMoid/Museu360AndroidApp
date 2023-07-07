package com.example.museu360.ui.theme

import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebView

class MyJavaScriptInterface(private val context: Context) {

    @JavascriptInterface
    fun clearCache() {
        val webView = WebView(context)
        webView.clearCache(true)
    }

    // Adicione outros métodos conforme necessário
}