package com.example.playlistmaker

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface ItunesApi {
    @GET("/search?entity=song")
    fun search(@Query("term") term: String): Call<TrackResponse>
}