package com.example.bsitfinalmobapp

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReportsActivity : AppCompatActivity() {

    private lateinit var portfolioText: TextView
    private lateinit var updatedSourcesWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports)

        portfolioText = findViewById(R.id.portfolio_text)
        updatedSourcesWebView = findViewById(R.id.sources_webview)

        // Set portfolio content
        portfolioText.text = """
📊 Portfolio Reports:

🌾 • Crop Yield Summary (Monthly)  
🌧️ • Rainfall & Temperature Correlation  
🐛 • Disease and Pest Impact Logs  
👨‍🌾 • Farmer Activities Summary  
📈 • Market Price Fluctuation Trends  
🌱 • Fertilizer Usage Statistics  
🔮 • Harvest Forecast Reports  
🧪 • Soil Health and Nutrient Profile  
🌽 • Crop Variety Performance Comparison  
🚜 • Equipment and Machinery Usage Log  
💧 • Irrigation Schedule Monitoring  
👥 • Farm Labor Allocation Overview  
🚚 • Supply Chain and Distribution Map  
💰 • Government Subsidy Tracker  
⚠️ • Climate Risk and Alert Reports  
📉 • Post-Harvest Loss Analysis  
💹 • Agribusiness Profitability Dashboard  
🌿 • Organic vs Chemical Input Ratio  
🤝 • Community Cooperative Contributions  
📚 • Training and Agricultural Extension Logs
""".trimIndent()

        // Load DA.gov.ph or other sources
        updatedSourcesWebView.webViewClient = WebViewClient()
        updatedSourcesWebView.settings.javaScriptEnabled = true
        updatedSourcesWebView.loadUrl("https://www.da.gov.ph")
    }
}
