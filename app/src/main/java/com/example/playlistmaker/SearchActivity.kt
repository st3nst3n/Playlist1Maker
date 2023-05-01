package com.example.playlistmaker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    private val iTunesBaseUrl = "https://itunes.apple.com"

    private val iTunesRetrofit =
        Retrofit.Builder().baseUrl(iTunesBaseUrl).addConverterFactory(GsonConverterFactory.create())
            .build()
    private val iTunesService = iTunesRetrofit.create(ItunesApi::class.java)

    private lateinit var arrowBack: ImageView
    private lateinit var inputEditText: EditText
    private lateinit var clearButton: ImageView
    private lateinit var placeholderMessageNothingFound: LinearLayout
    private lateinit var placeholderMessageNoInternet: LinearLayout
    private lateinit var refreshButton: Button
    private lateinit var trackList: RecyclerView

    enum class RequestStatus {
        SUCCESS, NOTHING_FOUND, NO_INTERNET
    }


    private val trackAdapter = TrackAdapter()
    private var inputText = ""


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
        findViewByIdMethod()
        setOnClickListenerMethod()
        trackList.adapter = trackAdapter
        inputEditText.setText(inputText)

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                inputText = s.toString()
                clearButton.visibility = clearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
                //empty
            }

        }
        inputEditText.setText(inputText)
        inputEditText.addTextChangedListener(simpleTextWatcher)
    }

    private fun findViewByIdMethod() {
        refreshButton = findViewById(R.id.refresh_button)
        trackList = findViewById(R.id.trackList)
        arrowBack = findViewById(R.id.back_arrow)
        inputEditText = findViewById(R.id.searchEditText)
        clearButton = findViewById(R.id.clear_button)
        placeholderMessageNothingFound = findViewById(R.id.nothing_found)
        placeholderMessageNoInternet = findViewById(R.id.no_internet)
    }

    private fun setOnClickListenerMethod() {

        arrowBack.setOnClickListener {
            val arrowIntent = Intent(this, MainActivity::class.java)
            startActivity(arrowIntent)
            finish()
        }

        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val inputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
                inputMethodManager?.hideSoftInputFromWindow(inputEditText.windowToken, 0)
                searchTracks()
                true
            } else false
        }

        refreshButton.setOnClickListener { searchTracks() }

        clearButton.setOnClickListener {
            trackAdapter.setTracks(null)
            inputEditText.setText("")
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(inputEditText.windowToken, 0)
            searchResultsProcessing(RequestStatus.SUCCESS)
            placeholderMessageNothingFound.visibility = View.GONE
            it.hideKeyboard()
        }
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

    private fun searchTracks() {
        iTunesService.search(inputEditText.text.toString())
            .enqueue(object : Callback<TrackResponse> {
                override fun onResponse(
                    call: Call<TrackResponse>,
                    response: Response<TrackResponse>
                ) {
                    if (response.code() == 200) {
                        if (response.body()?.results?.isNotEmpty() == true) {
                            trackAdapter.setTracks(response.body()?.results!!)
                            searchResultsProcessing(RequestStatus.SUCCESS)
                        } else {
                            trackAdapter.setTracks(null)
                            searchResultsProcessing(RequestStatus.NOTHING_FOUND)
                        }
                    }
                }

                override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                    searchResultsProcessing(RequestStatus.NO_INTERNET)
                }
            })
    }

    private fun searchResultsProcessing(requestStatus: RequestStatus) {
        when (requestStatus) {
            RequestStatus.SUCCESS -> {
                placeholderMessageNothingFound.visibility = View.INVISIBLE
                placeholderMessageNoInternet.visibility = View.INVISIBLE
                refreshButton.visibility = View.INVISIBLE
            }
            RequestStatus.NOTHING_FOUND -> {
                placeholderMessageNothingFound.visibility = View.VISIBLE
                refreshButton.visibility = View.GONE
            }
            RequestStatus.NO_INTERNET -> {
                trackAdapter.setTracks(null)
                placeholderMessageNoInternet.visibility = View.VISIBLE
                refreshButton.visibility = View.VISIBLE
            }
        }

    }

}





