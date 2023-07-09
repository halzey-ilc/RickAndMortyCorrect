package com.example.rickandmortycorrect.utils

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmortycorrect.R
import com.example.rickandmortycorrect.domain.model.CharactersDomain
import com.example.rickandmortycorrect.domain.model.EpisodeDomain
import com.example.rickandmortycorrect.presentation.episode.adapter.EpisodeAdapter
import com.example.rickandmortycorrect.presentation.location.adapter.LocationDetailAdapter


@BindingAdapter("imageUrl")
fun BuildingAdapter(imageView: ImageView, url: String) {
    url.let {
        imageView.load(url) {
            crossfade(true)
                .error(R.drawable.error_image)
                .placeholder(R.drawable.animate_loading)
        }
    }
}

@BindingAdapter("bindingEpisodeList")
fun bindEpisodeList(recyclerView: RecyclerView, episodeList: List<EpisodeDomain>?) {
    if (!episodeList.isNullOrEmpty()) {
        val adapter = recyclerView.adapter as EpisodeAdapter
        adapter.submitList(episodeList)
    }
}

@BindingAdapter("isVisible")
fun isFilter(view: View, isFilter: Boolean) {
    view.visibility = if (isFilter) View.VISIBLE else View.GONE
}

@BindingAdapter("bindCharacterList")
fun bindCharacterList(recyclerView: RecyclerView, characters: List<CharactersDomain>?) {
    if (!characters.isNullOrEmpty()) {

        val adapter = recyclerView.adapter as LocationDetailAdapter
        adapter.submitList(characters)
    }
}

@BindingAdapter("isLoading")
fun isLoading(progressBar: ProgressBar, isLoading: Boolean) {

    if (isLoading) {
        progressBar.visibility = View.VISIBLE
    } else {
        progressBar.visibility = View.GONE
    }
}


// We determine the color according to the status of the characters.
@BindingAdapter("statusColor")
fun changeColor(card: CardView, status: String) {

    if (status == "Dead") {
        card.setCardBackgroundColor(Color.RED)
    } else if (status == "Alive") {
        card.setCardBackgroundColor(Color.GREEN)
    } else {
        card.setCardBackgroundColor(Color.GRAY)
    }
}

@BindingAdapter("statusColor")
fun changeColor(textView: TextView, status: String) {

    if (status == "Dead") {
        textView.setTextColor(Color.RED)
    } else if (status == "Alive") {
        textView.setTextColor(Color.GREEN)
    } else {
        textView.setTextColor(Color.GRAY)
    }
}