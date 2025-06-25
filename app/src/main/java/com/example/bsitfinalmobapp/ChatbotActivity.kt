package com.example.bsitfinalmobapp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ChatbotActivity : AppCompatActivity() {

    private lateinit var chatBox: LinearLayout
    private lateinit var editText: EditText
    private lateinit var sendButton: Button
    private lateinit var scrollView: ScrollView

    private val suggestions = arrayOf(
        "Hello",
        "What is the weather forecast?",
        "Show crop guide",
        "Market prices today",
        "Farmer forum info",
        "Analytics data",
        "Thanks",
        "Goodbye"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)

        chatBox = findViewById(R.id.chatBox)
        editText = findViewById(R.id.editText)
        sendButton = findViewById(R.id.sendButton)
        scrollView = findViewById(R.id.scrollView)

        editText.isFocusable = false
        editText.isClickable = true

        editText.setOnClickListener {
            showSuggestionDialog()
        }

        sendButton.setOnClickListener {
            val userInput = editText.text.toString().trim()
            if (userInput.isNotEmpty()) {
                addMessage(false, "You: $userInput")
                respondLocally(userInput)
                editText.setText("")
                scrollToBottom()
            }
        }

        findViewById<Button>(R.id.btnBackHome).setOnClickListener {
            finish()
        }
    }

    private fun showSuggestionDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Choose a Question")
            .setItems(suggestions) { _, which ->
                editText.setText(suggestions[which])
            }
            .show()
    }

    private fun respondLocally(userInput: String) {
        val response = getLocalResponse(userInput.lowercase())
        addMessage(true, "AgriBot: $response")
        scrollToBottom()
    }

    private fun getLocalResponse(input: String): String {
        return when {
            input.contains("hello") || input.contains("hi") -> "Hello there! How can I assist you today in farming?"
            input.contains("weather") -> "You can check the latest weather forecast using the Weather button on the home screen."
            input.contains("crop") && input.contains("guide") -> "Use the Crop Guide feature to learn the best planting practices."
            input.contains("market") || input.contains("price") -> "Go to Market Prices to view up-to-date crop prices."
            input.contains("forum") -> "Join the Farmer Forum to ask and share knowledge with fellow farmers."
            input.contains("analytics") -> "View graphical data about crop trends in the Analytics section."
            input.contains("thank") -> "You're welcome! 🌾"
            input.contains("bye") || input.contains("exit") -> "Goodbye! Happy farming! 👩‍🌾👨‍🌾"
            else -> "I'm not sure how to respond to that yet. Try asking about weather, crops, or market prices."
        }
    }

    private fun scrollToBottom() {
        scrollView.post {
            scrollView.fullScroll(View.FOCUS_DOWN)
        }
    }

    private fun addMessage(isBot: Boolean, message: String) {
        val messageLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(8, 8, 8, 8)
        }

        if (isBot) {
            val image = ImageView(this).apply {
                setImageResource(R.drawable.logo1)
                layoutParams = LinearLayout.LayoutParams(80, 80)
            }
            messageLayout.addView(image)
        }

        val textView = TextView(this).apply {
            text = message
            textSize = 18f
            setPadding(16, 0, 0, 0)
        }

        messageLayout.addView(textView)
        chatBox.addView(messageLayout)
    }
}
