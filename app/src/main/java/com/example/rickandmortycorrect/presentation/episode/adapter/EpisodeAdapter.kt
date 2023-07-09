package com.example.rickandmortycorrect.presentation.episode.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortycorrect.databinding.EpisodeItemRowBinding
import com.example.rickandmortycorrect.domain.model.EpisodeDomain

class EpisodeAdapter : androidx.recyclerview.widget.ListAdapter<EpisodeDomain, EpisodeAdapter.EpisodeViewHolder>(Diff()) {


    class EpisodeViewHolder(val binding: EpisodeItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): EpisodeViewHolder {
                val binding = EpisodeItemRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return EpisodeViewHolder(binding)
            }
        }

        fun bind(episode: EpisodeDomain) {
            binding.episode = episode
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {

        val episode = getItem(position)
        holder.bind(episode)
    }


    class Diff() : DiffUtil.ItemCallback<EpisodeDomain>() {
        override fun areItemsTheSame(oldItem: EpisodeDomain, newItem: EpisodeDomain): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EpisodeDomain, newItem: EpisodeDomain): Boolean {
            return oldItem == newItem
        }

    }

}