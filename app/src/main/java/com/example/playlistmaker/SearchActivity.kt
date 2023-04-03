package com.example.playlistmaker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class SearchActivity : AppCompatActivity() {

    private lateinit var arrowBack: ImageView
    private lateinit var inputEditText: EditText
    private lateinit var clearButton: ImageView

    companion object {
        const val USER_INPUT = "USER_INPUT"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(USER_INPUT, inputEditText.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        inputEditText.setText(savedInstanceState.getString(USER_INPUT))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        arrowBack = findViewById(R.id.back_arrow)
        arrowBack.setOnClickListener {
            val arrowIntent = Intent(this, MainActivity::class.java)
            startActivity(arrowIntent)
        }
        inputEditText = findViewById(R.id.searchEditText)
        clearButton = findViewById(R.id.clear_button)
        clearButton.setOnClickListener {
            inputEditText.setText("")
            it.hideKeyboard()
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = clearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
                //empty
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)


        val trackAdapter = TrackAdapter(
            arrayListOf(
                Track(
                    getString(R.string.track_name_1),
                    getString(R.string.band_name_1),
                    getString(R.string.track_time_1),
                    getString(R.string.track_poster_1)
                ),
                Track(
                    getString(R.string.track_name_2),
                    getString(R.string.band_name_2),
                    getString(R.string.track_time_2),
                    getString(R.string.track_poster_2)
                ),
                Track(
                    getString(R.string.track_name_3),
                    getString(R.string.band_name_3),
                    getString(R.string.track_time_3),
                    getString(R.string.track_poster_3)
                ),
                Track(
                    getString(R.string.track_name_4),
                    getString(R.string.band_name_4),
                    getString(R.string.track_time_4),
                    getString(R.string.track_poster_4)
                ),
                Track(
                    getString(R.string.track_name_5),
                    getString(R.string.band_name_5),
                    getString(R.string.track_time_5),
                    getString(R.string.track_poster_5)
                )
            )
        )

        val trackList = findViewById<RecyclerView>(R.id.trackList)
        trackList.adapter = trackAdapter


    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }

    }

    private fun View.hideKeyboard() {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(windowToken, 0)
    }
}

