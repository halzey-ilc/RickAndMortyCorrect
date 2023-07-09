package com.example.rickandmortycorrect.presentation.favorite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortycorrect.R
import com.example.rickandmortycorrect.databinding.CharacterItemRecyclerBinding
import com.example.rickandmortycorrect.domain.model.CharactersDomain
import com.example.rickandmortycorrect.presentation.character.adapter.DiffUtilCallBack
import com.example.rickandmortycorrect.presentation.character.adapter.FROMCHARACTERLIST
import com.example.rickandmortycorrect.presentation.character.adapter.FROMFAVORITELIST
import com.example.rickandmortycorrect.presentation.character.view.CharacterListFragmentDirections
import com.example.rickandmortycorrect.presentation.favorite.view.FavoriteListFragmentDirections

class FavoriteCharacterAdapter :
    ListAdapter<CharactersDomain, FavoriteCharacterAdapter.CharacterViewHolder>(DiffUtilCallBack()) {


    class CharacterViewHolder(val binding: CharacterItemRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.characterModel?.id?.let { id ->
                    navigateToCharacterDetail(id, it, FROMFAVORITELIST)
                }
            }

        }
        fun navigateToCharacterDetail(id: Int, view: View, whichFragment: String) {

            val direction = if (FROMCHARACTERLIST == whichFragment) {
                CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
                    id
                )
            } else {
                FavoriteListFragmentDirections.actionFavoriteListFragmentToCharacterDetailFragment(
                    id
                )
            }

            view.findNavController().navigate(direction)
        }

        companion object {
            fun create(parent: ViewGroup): CharacterViewHolder {
                val binding = CharacterItemRecyclerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return CharacterViewHolder(binding)
            }
        }

        fun bind(charactersDomain: CharactersDomain) {
            binding.characterModel = charactersDomain
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.create(parent)
    }


    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val charactersDomain = getItem(position)
        holder.bind(charactersDomain)

        holder.itemView.animation = AnimationUtils.loadAnimation(
            holder.itemView.context,
            R.anim.up_anim
        )
    }
}

