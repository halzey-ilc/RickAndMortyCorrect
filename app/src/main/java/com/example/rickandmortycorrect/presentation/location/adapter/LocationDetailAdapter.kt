package com.example.rickandmortycorrect.presentation.location.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortycorrect.databinding.CharacterItemRecyclerBinding
import com.example.rickandmortycorrect.domain.model.CharactersDomain
import com.example.rickandmortycorrect.utils.ItemClickListener

class LocationDetailAdapter(private val onClickListener: ItemClickListener) :
    androidx.recyclerview.widget.ListAdapter<CharactersDomain, LocationDetailAdapter.LocationDetailViewHolder>(DiffUtilLocation()) {

    class LocationDetailViewHolder(val binding: CharacterItemRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): LocationDetailViewHolder {
                val binding =
                    CharacterItemRecyclerBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return LocationDetailViewHolder(binding)
            }
        }

        fun bind(charactersDomain: CharactersDomain) {
            binding.characterModel = charactersDomain
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationDetailViewHolder {
        return LocationDetailViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: LocationDetailViewHolder, position: Int) {
        val charactersDomain = getItem(position)

        holder.bind(charactersDomain)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(charactersDomain.id)
        }
    }

}


    class DiffUtilLocation() : DiffUtil.ItemCallback<CharactersDomain>() {
        override fun areItemsTheSame(
            oldItem: CharactersDomain,
            newItem: CharactersDomain
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CharactersDomain,
            newItem: CharactersDomain
        ): Boolean {
            return oldItem == newItem
        }


    }