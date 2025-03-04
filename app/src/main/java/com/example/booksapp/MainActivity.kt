package com.example.booksapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        loadSignUpFragment()
    }

    private fun loadSignUpFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.auth_fragment_container,SignUpFragment()).commit()

    }
}