package com.example.playlistmaker

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.*

class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val artworkUrl100: ImageView = itemView.findViewById(R.id.poster_id)
    private val trackName: TextView = itemView.findViewById(R.id.track_name)
    private val artistName: TextView = itemView.findViewById(R.id.band_name)
    private val trackTimeMillis: TextView = itemView.findViewById(R.id.track_length)

    fun bind(model: Track) {
        trackName.text = model.trackName
        artistName.text = model.artistName
        trackTimeMillis.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(model.trackTimeMillis.toLong())
        Glide.with(itemView)
            .load(model.artworkUrl100)
            .centerCrop()
            .transform(RoundedCorners(4))
            .placeholder(R.drawable.placeholder)
            .into(artworkUrl100)
    }
}