package com.example.bsitfinalmobapp

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class SourcesActivity : AppCompatActivity() {

    private lateinit var sourceWebView: WebView
    private lateinit var sourceSpinner: Spinner

    private val sourceMap = mapOf(
        "Department of Agriculture" to "https://www.da.gov.ph/",
        "Philippine Statistics Authority" to "https://psa.gov.ph/",
        "FAO Philippines" to "https://www.fao.org/philippines",
        "Philippine Rice Research Institute" to "https://www.philrice.gov.ph/",
        "Philippine Coconut Authority" to "https://pca.gov.ph/",
        "Sugar Regulatory Administration" to "https://www.sra.gov.ph/"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sources)

        sourceSpinner = findViewById(R.id.source_spinner)
        sourceWebView = findViewById(R.id.source_webview)

        sourceWebView.webViewClient = WebViewClient()
        sourceWebView.settings.javaScriptEnabled = true

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            sourceMap.keys.toList()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sourceSpinner.adapter = adapter

        sourceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                val selected = sourceSpinner.selectedItem.toString()
                val url = sourceMap[selected]
                url?.let { sourceWebView.loadUrl(it) }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}
