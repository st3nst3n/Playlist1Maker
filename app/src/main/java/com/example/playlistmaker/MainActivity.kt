package com.example.playlistmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val search = findViewById<Button>(R.id.search_button)
        val searchClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(p0: View?) {
                Toast.makeText(this@MainActivity, "Начинаем искать!", Toast.LENGTH_SHORT).show()
            }
        }
        val library = findViewById<Button>(R.id.library_button)
        library.setOnClickListener {
            Toast.makeText(this@MainActivity, "Заходим в медиатеку", Toast.LENGTH_SHORT).show()
        }
        val settings = findViewById<Button>(R.id.settings_button)
        settings.setOnClickListener {
            Toast.makeText(this@MainActivity, "Заходим в настройки", Toast.LENGTH_SHORT).show()
        }
        search.setOnClickListener(searchClickListener)
    }
}