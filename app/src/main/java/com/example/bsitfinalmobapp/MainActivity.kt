package com.example.bsitfinalmobapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var infoText: TextView
    private lateinit var loadingGif: ImageView
    private lateinit var assistantLabel: TextView
    private lateinit var logoutBtn: Button
    private lateinit var userEmailText: TextView  // 👈 added

    private val slogans = listOf(
        "\"Love Farmer Agriculture\"",
        "\"Grow with Nature\"",
        "\"Plant Today, Harvest Tomorrow\"",
        "\"Sow. Nurture. Reap.\"",
        "\"Today is June\""
    )
    private var sloganIndex = 0
    private val handler = Handler(Looper.getMainLooper())
    private val sloganRunnable = object : Runnable {
        override fun run() {
            assistantLabel.text = slogans[sloganIndex]
            sloganIndex = (sloganIndex + 1) % slogans.size
            handler.postDelayed(this, 2000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_main)


        loadingGif = findViewById(R.id.loadingGif)
        assistantLabel = findViewById(R.id.assistantLabel)
        logoutBtn = findViewById(R.id.btnLogout)
        userEmailText = findViewById(R.id.tvUserEmail) // 👈 bind the new TextView

        // ✅ Show current user email
        userEmailText.text = "Logged in as: ${currentUser.email}"

        // Load GIF
        Glide.with(this)
            .asGif()
            .load(R.drawable.farmer)
            .into(loadingGif)

        handler.post(sloganRunnable)

        findViewById<Button>(R.id.btnWeather).setOnClickListener {
            startActivity(Intent(this, WeatherActivity::class.java))
        }

        findViewById<Button>(R.id.btnCropGuide).setOnClickListener {
            startActivity(Intent(this, CropGuideActivity::class.java))
        }

        findViewById<Button>(R.id.btnMarket).setOnClickListener {
            startActivity(Intent(this, MarketActivity::class.java))
        }

        findViewById<Button>(R.id.btnForum).setOnClickListener {
            startActivity(Intent(this, ForumActivity::class.java))
        }

        findViewById<Button>(R.id.btnAnalytics).setOnClickListener {
            startActivity(Intent(this, AnalyticsActivity::class.java))
        }
        findViewById<Button>(R.id.btnMap).setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        findViewById<Button>(R.id.btnReports).setOnClickListener {
            startActivity(Intent(this, ReportsActivity::class.java))
        }

        findViewById<FloatingActionButton>(R.id.fabChat).setOnClickListener {
            startActivity(Intent(this, ChatbotActivity::class.java))
        }

        findViewById<Button>(R.id.btnSources).setOnClickListener {
            startActivity(Intent(this, SourcesActivity::class.java))
        }

        logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
