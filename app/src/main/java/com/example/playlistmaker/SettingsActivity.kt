package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val arrowBack = findViewById<ImageView>(R.id.back_arrow)
        arrowBack.setOnClickListener {
            val arrowIntent = Intent(this, MainActivity::class.java)
            startActivity(arrowIntent)
        }

        val shareApp = findViewById<TextView>(R.id.shareButton)
        shareApp.setOnClickListener {
            val shareText = getString(R.string.link_to_the_course)
            val sendText = Intent(Intent.ACTION_SEND)
            sendText.putExtra(Intent.EXTRA_TEXT, shareText)
            sendText.type = "text/plain"
            val shareIntent = Intent.createChooser(sendText, null)
            startActivity(shareIntent)
        }

        val sendToSupport = findViewById<TextView>(R.id.supportButton)
        sendToSupport.setOnClickListener {
            val lineTitle = getString(R.string.letter_title)
            val lineMessage = getString(R.string.letter_body)
            val shareIntent = Intent(Intent.ACTION_SENDTO)
            shareIntent.data = Uri.parse("mailto:")
            shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.developer_mail)))
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, lineTitle)
            shareIntent.putExtra(Intent.EXTRA_TEXT, lineMessage)
            startActivity(shareIntent)
        }

        val readTerms = findViewById<TextView>(R.id.termsButton)
        readTerms.setOnClickListener {
            val openTerms = Intent(Intent.ACTION_VIEW)
            openTerms.data = Uri.parse(getString(R.string.practicum_offer))
            startActivity(openTerms)
        }

    }
}