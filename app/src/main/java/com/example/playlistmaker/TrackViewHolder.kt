package com.example.playlistmaker

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val posterV: ImageView = itemView.findViewById(R.id.poster_id)
    private val trackV: TextView = itemView.findViewById(R.id.track_name)
    private val bandV: TextView = itemView.findViewById(R.id.band_name)
    private val trackLengthV: TextView = itemView.findViewById(R.id.track_length)

    fun bind(model: Track) {
        trackV.text = model.trackName
        bandV.text = model.artistName
        trackLengthV.text = model.trackTime
        Glide.with(itemView)
            .load(model.artworkUrl100)
            .centerCrop()
            .transform(RoundedCorners(4))
            .placeholder(R.drawable.placeholder)
            .into(posterV)
    }
}