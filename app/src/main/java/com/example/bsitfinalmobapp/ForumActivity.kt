package com.example.bsitfinalmobapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding

class ForumActivity : AppCompatActivity() {

    data class ForumPost(val user: String, val message: String, val source: String)

    private val dummyPosts = listOf(
        ForumPost("FarmerJuan", "Ano po magandang pananim sa El Niño ngayong 2025?", "DA Advisory, Jan 2025"),
        ForumPost("AgriTechPH", "Gamitin ang drought-resistant seeds tulad ng NSIC Rc222. (Source: PhilRice)", "PhilRice, March 2025"),
        ForumPost("PlantitaGrace", "Nakatulong po sa akin ang urban gardening gamit ang container. Try nyo po!", "DA Urban Farming Guide 2025"),
        ForumPost("AgriYouth2025", "May webinar po sa June tungkol sa digital farming sa Pilipinas. Libre po ito!", "DA Facebook Page, June 2025"),
        ForumPost("KakabsatTani", "Mas okay po ang direct seeding sa dry season ngayon. Mas tipid sa tubig.", "PhilRice Extension, 2025")
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum)

        val container = findViewById<LinearLayout>(R.id.forumContainer)

        for (post in dummyPosts) {
            val userView = TextView(this).apply {
                text = post.user
                textSize = 14f
                setPadding(8)
                setTextColor(0xFF2E7D32.toInt())
                setTypeface(null, android.graphics.Typeface.BOLD)
            }

            val messageView = TextView(this).apply {
                text = post.message
                textSize = 16f
                setPadding(8)
                setTextColor(0xFF212121.toInt())
            }

            val sourceView = TextView(this).apply {
                text = "📌 Source: ${post.source}"
                textSize = 12f
                setPadding(8)
                setTextColor(0xFF757575.toInt())
            }

            val postLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(16)
                setBackgroundColor(0xFFFFFFFF.toInt())
                elevation = 4f
                val marginParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                marginParams.setMargins(0, 0, 0, 16)
                layoutParams = marginParams

                addView(userView)
                addView(messageView)
                addView(sourceView)
            }

            container.addView(postLayout)
        }
    }
}
