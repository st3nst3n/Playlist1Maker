package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val arrowBack = findViewById<ImageView>(R.id.back_arrow)
        arrowBack.setOnClickListener {
            val arrowIntent = Intent(this, MainActivity::class.java)
            startActivity(arrowIntent)
            finish()
        }

    }
}