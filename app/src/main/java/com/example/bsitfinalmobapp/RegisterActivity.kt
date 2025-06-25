package com.example.bsitfinalmobapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var registerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register) // Your custom ScrollView layout

        auth = FirebaseAuth.getInstance()

        emailField = findViewById(R.id.etEmail)
        passwordField = findViewById(R.id.etPassword)
        registerBtn = findViewById(R.id.btnRegister)

        registerBtn.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (email.isNotEmpty() && password.length >= 6) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        val user = auth.currentUser
                        user?.sendEmailVerification()?.addOnSuccessListener {
                            Toast.makeText(this, "Registered successfully. Please check your email to verify your account.", Toast.LENGTH_LONG).show()
                            auth.signOut()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }?.addOnFailureListener {
                            Toast.makeText(this, "Verification email failed: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Registration failed: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
